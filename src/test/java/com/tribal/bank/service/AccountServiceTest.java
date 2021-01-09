package com.tribal.bank.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tribal.bank.constants.MoneyTransferErrorCodes;
import com.tribal.bank.entity.Account;
import com.tribal.bank.entity.AccountType;
import com.tribal.bank.entity.Branch;
import com.tribal.bank.entity.Customer;
import com.tribal.bank.exception.TribalBankingException;
import com.tribal.bank.repo.AccountRepository;
import com.tribal.bank.repo.CustomerRepository;
import com.tribal.bank.response.dto.AccountDetailsDTO;
import com.tribal.bank.service.impl.AccountServiceImpl;
@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
	@Mock
	private  CustomerRepository customerRepository;

	@Mock
	private  AccountRepository accountRepository;

	@InjectMocks
	AccountServiceImpl accountService;

	private Customer customer;
	private Account account;
	private Account disabledAccount;

	@BeforeEach
	public void init(){
		List<Account> accounts = new ArrayList<>();
		account = Account.builder().active(true).accountNumber("1111").balance(BigDecimal.ONE).accountTypeId(new AccountType()).branch(new Branch()).build();
		accounts.add(account);
		disabledAccount = Account.builder().active(false).accountNumber("1112").balance(BigDecimal.ONE).accountTypeId(new AccountType()).branch(new Branch()).build();
		accounts.add(disabledAccount);
		customer = Customer.builder().active(true).userName("testuser").accounts(accounts).build();
	}

	@Test
	public void accountDetailsSuccess() throws TribalBankingException {
		Mockito.when(customerRepository.findByUserName(Mockito.contains("testuser"))).thenReturn(Optional.of(customer));
		Mockito.when(accountRepository.findByAccountNumber(Mockito.matches("1111"))).thenReturn(Optional.of(account));
		AccountDetailsDTO account = accountService.accountDetails("testuser", "1111");
		assertEquals(account.getBalance(),BigDecimal.ONE);
	}

	@Test
	public void accountDetailsAccountDisabled() throws TribalBankingException {
		Mockito.when(customerRepository.findByUserName(Mockito.contains("testuser"))).thenReturn(Optional.of(customer));
		Mockito.when(accountRepository.findByAccountNumber(Mockito.matches("1112"))).thenReturn(Optional.of(disabledAccount));
		try {
			accountService.accountDetails("testuser", "1112");
		}
		catch(TribalBankingException e ) {
			assertEquals(e.getErrorCode(),MoneyTransferErrorCodes.ACCOUNT_IS_NOT_ACTIVE.getErrorCode());
			assertEquals(e.getMessage(),MoneyTransferErrorCodes.ACCOUNT_IS_NOT_ACTIVE.getErrorDescription());
		}
	}

}
