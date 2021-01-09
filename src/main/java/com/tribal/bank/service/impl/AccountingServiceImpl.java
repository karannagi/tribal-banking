package com.tribal.bank.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tribal.bank.constants.MoneyTransferErrorCodes;
import com.tribal.bank.entity.Account;
import com.tribal.bank.exception.TribalBankingException;
import com.tribal.bank.helper.CustomerValidationHelper;
import com.tribal.bank.repo.AccountRepository;
import com.tribal.bank.response.dto.AccountingServiceResponse;
import com.tribal.bank.service.AccountingService;
import com.tribal.bank.service.LedgerEntryService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class AccountingServiceImpl implements AccountingService {


	private final LedgerEntryService transactionLogService;
	private final AccountRepository accountRepository;

	/*
	 * Performs the credit transaction while checking for overdraft and pessimistic write lock
	 * */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public AccountingServiceResponse creditAccount(final String accountNumber, final BigDecimal amount,final String remarks,String referenceNumber) throws TribalBankingException {
		
		Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->
		new TribalBankingException(MoneyTransferErrorCodes.CREDIT_ACCOUNT_IS_NOT_FOUND));
		log.info("Loaded credit account for the transaction {}",referenceNumber);
		CustomerValidationHelper.checkIfAccountIsActive(account,true);
		BigDecimal balance = account.getBalance().add(amount);
		account.setBalance(balance);
		
		
		transactionLogService.logLedgerEntry(account.getAccountNumber(), amount, remarks,referenceNumber);
		return AccountingServiceResponse.builder().build();
	}
	/*
	 * Performs the debit transaction while checking for overdraft and pessimistic write lock
	 * */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public AccountingServiceResponse debitAccount(final String accountNumber, final BigDecimal amount,final String remarks,String referenceNumber) throws TribalBankingException {
		
		Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->
		new TribalBankingException(MoneyTransferErrorCodes.DEBIT_ACCOUNT_IS_NOT_FOUND));
		log.info("Loaded debit account for the transaction {}",referenceNumber);
		CustomerValidationHelper.checkIfAccountIsActive(account,false);
		BigDecimal balance = account.getBalance().subtract(amount);
		//check for overdraft
		if(balance.compareTo(BigDecimal.ZERO)==-1)
			throw new TribalBankingException(MoneyTransferErrorCodes.INSUFFICIENT_BALANCE);
		
		account.setBalance(balance);
		
		transactionLogService.logLedgerEntry(account.getAccountNumber(), amount, remarks,referenceNumber);
		return AccountingServiceResponse.builder().build();
	}

}
