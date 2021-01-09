package com.tribal.bank.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tribal.bank.constants.MoneyTransferErrorCodes;
import com.tribal.bank.constants.TransactionStatus;
import com.tribal.bank.entity.Customer;
import com.tribal.bank.exception.TribalBankingException;
import com.tribal.bank.helper.CustomerValidationHelper;
import com.tribal.bank.helper.MoneyTransferHelper;
import com.tribal.bank.repo.CustomerRepository;
import com.tribal.bank.request.dto.MoneyTransferRequestDTO;
import com.tribal.bank.response.dto.MoneyTransferResponseDTO;
import com.tribal.bank.service.AccountingService;
import com.tribal.bank.service.MoneyTransferService;
import com.tribal.bank.service.TransactionLogService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class MoneyTransferServiceImpl implements MoneyTransferService {

	private final CustomerRepository customerRepository;
	private final AccountingService accountingService;
	private final TransactionLogService transactionLogService;


	@Override
	@Transactional(rollbackFor =TribalBankingException.class,propagation = Propagation.REQUIRED)
	public  MoneyTransferResponseDTO moneyTransfer(String userName, MoneyTransferRequestDTO moneyTransferRequest) throws TribalBankingException {
		final Customer customer = customerRepository.findByUserName(userName).orElseThrow(()->new TribalBankingException(MoneyTransferErrorCodes.CUSTOMER_NOT_FOUND));
		CustomerValidationHelper.accountBelongsToUser(customer, moneyTransferRequest.getAccountFrom());
		log.info("Loaded customer for the principal {}",userName);
		final String referenceNumber = MoneyTransferHelper.generateReferenceNumber();
		log.info("Reference number for this request is: {}",referenceNumber);
		try {
			performMoneyTransfer(moneyTransferRequest,referenceNumber);
			transactionLogService.logTransferTransaction(customer.getId(), moneyTransferRequest.getAccountFrom(), moneyTransferRequest.getAccountTo(), moneyTransferRequest.getAmount(), 
					MoneyTransferHelper.createMoneyTransferRemarks(moneyTransferRequest.getAccountFrom(),
							moneyTransferRequest.getAccountTo(),moneyTransferRequest.getRemarks()), TransactionStatus.SUCCESS, referenceNumber, StringUtils.EMPTY);
		}
		catch(TribalBankingException e) {
			transactionLogService.logTransferTransaction(customer.getId(), moneyTransferRequest.getAccountFrom(), moneyTransferRequest.getAccountTo(), moneyTransferRequest.getAmount(), 
					StringUtils.EMPTY, TransactionStatus.REJECTED, referenceNumber, e.getMessage());
			throw e;
		}
		catch(Exception e) {
			throw new TribalBankingException(MoneyTransferErrorCodes.UNKOWN_ERROR);
		}

		return MoneyTransferResponseDTO.builder().referenceNumber(referenceNumber).build();
	}
	private void performMoneyTransfer(MoneyTransferRequestDTO moneyTransferRequest,String referenceNumber) throws TribalBankingException {

		
		accountingService.debitAccount(moneyTransferRequest.getAccountFrom(), moneyTransferRequest.getAmount(), 
				MoneyTransferHelper.createDebitRemarks(moneyTransferRequest.getAccountFrom(), moneyTransferRequest.getAccountTo()),referenceNumber);
		log.info("Debit transaction is successful for ref no {}",referenceNumber);

		try{
		accountingService.creditAccount(moneyTransferRequest.getAccountTo(), moneyTransferRequest.getAmount(),
				MoneyTransferHelper.createCreditRemarks(moneyTransferRequest.getAccountFrom(), moneyTransferRequest.getAccountTo()),referenceNumber);
		log.info("Credit transaction is successful for ref no {}",referenceNumber);
		}
		catch(TribalBankingException e) {
			log.info("Credit transaction is not successful for ref no {} initiating reverse transfer",referenceNumber);
			accountingService.creditAccount(moneyTransferRequest.getAccountFrom(), moneyTransferRequest.getAmount(), 
					MoneyTransferHelper.createReversalRemarks(moneyTransferRequest.getAccountTo()),referenceNumber);
			throw e;
		}

	}


}
