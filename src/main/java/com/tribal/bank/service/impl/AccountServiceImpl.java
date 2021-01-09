package com.tribal.bank.service.impl;

import org.springframework.stereotype.Service;

import com.tribal.bank.constants.MoneyTransferErrorCodes;
import com.tribal.bank.entity.Account;
import com.tribal.bank.entity.Customer;
import com.tribal.bank.exception.TribalBankingException;
import com.tribal.bank.helper.CustomerValidationHelper;
import com.tribal.bank.repo.AccountRepository;
import com.tribal.bank.repo.CustomerRepository;
import com.tribal.bank.response.dto.AccountDetailsDTO;
import com.tribal.bank.service.AccountService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
	private final CustomerRepository customerRepository;
	private final AccountRepository accountRepository;
	
	@Override
	public AccountDetailsDTO accountDetails(final String userName, final String accountNumber) throws TribalBankingException {
		final Customer customer = customerRepository.findByUserName(userName).orElseThrow(()->new TribalBankingException(MoneyTransferErrorCodes.CUSTOMER_NOT_FOUND));
		log.info("Loaded customer for the principal {}",userName);
		CustomerValidationHelper.accountBelongsToUser(customer, accountNumber);
		final Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->
		new TribalBankingException(MoneyTransferErrorCodes.ACCOUNT_IS_NOT_FOUND));
		log.info("Loaded account for the principal {}",userName);
		CustomerValidationHelper.checkIfAccountIsActive(account,null);
		return AccountDetailsDTO.builder().balance(account.getBalance())
				.accountType(account.getAccountTypeId().getAccountName())
				.branch(account.getBranch().getName())
				.build();
	}

}
