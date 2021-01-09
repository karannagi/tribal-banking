package com.tribal.bank.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.tribal.bank.repo.CustomerRepository;
import com.tribal.bank.request.dto.MoneyTransferRequestDTO;
import com.tribal.bank.response.dto.AccountingServiceResponse;
import com.tribal.bank.response.dto.MoneyTransferResponseDTO;
import com.tribal.bank.service.impl.MoneyTransferServiceImpl;

@ExtendWith(MockitoExtension.class)
public class MoneyTransferServiceTest {
	@Mock
	private  CustomerRepository customerRepository;
	@Mock
	private  AccountingService accountingService;
	@Mock
	private  TransactionLogService transactionLogService;
	
	@InjectMocks
	private MoneyTransferServiceImpl moneyTransferService;
	
	
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
	public void moneyTransferSuccess() throws TribalBankingException {
		Mockito.when(customerRepository.findByUserName(Mockito.contains("testuser"))).thenReturn(Optional.of(customer));
		Mockito.when(accountingService.debitAccount(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(new AccountingServiceResponse());
		Mockito.when(accountingService.creditAccount(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(new AccountingServiceResponse());
		MoneyTransferResponseDTO response = moneyTransferService.moneyTransfer("testuser", MoneyTransferRequestDTO.builder().accountFrom("1111").amount(BigDecimal.ONE).build());
		assertNotNull(response);
	}
	@Test
	public void moneyTransferFailureAccountMismatch() throws TribalBankingException {
		Mockito.when(customerRepository.findByUserName(Mockito.contains("testuser"))).thenReturn(Optional.of(customer));
		TribalBankingException exception = null;
		try {
		moneyTransferService.moneyTransfer("testuser", MoneyTransferRequestDTO.builder().accountFrom("1114").amount(BigDecimal.ONE).build());
		}
		catch(TribalBankingException e ) {
			exception = e;
		}
		assertEquals(exception.getMessage(),MoneyTransferErrorCodes.ACCOUNT_DOES_NOT_BELONG_TO_USER.getErrorDescription());
		assertEquals(exception.getErrorCode(),MoneyTransferErrorCodes.ACCOUNT_DOES_NOT_BELONG_TO_USER.getErrorCode());
	}
	@Test
	public void moneyTransferFailureDebitSuccessCreditFailure() throws TribalBankingException {
		Mockito.when(customerRepository.findByUserName(Mockito.contains("testuser"))).thenReturn(Optional.of(customer));
		Mockito.when(accountingService.debitAccount(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(new AccountingServiceResponse());
		
		Mockito.doAnswer(i->{
			throw new TribalBankingException(MoneyTransferErrorCodes.CREDIT_ACCOUNT_IS_NOT_FOUND);
		})
		.when(accountingService).creditAccount(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
		
		TribalBankingException exception = null;
		try {
		moneyTransferService.moneyTransfer("testuser", MoneyTransferRequestDTO.builder().accountFrom("1111").amount(BigDecimal.ONE).build());
		}
		catch(TribalBankingException e ) {
			exception = e;
		}
		assertEquals(exception.getMessage(),MoneyTransferErrorCodes.CREDIT_ACCOUNT_IS_NOT_FOUND.getErrorDescription());
		assertEquals(exception.getErrorCode(),MoneyTransferErrorCodes.CREDIT_ACCOUNT_IS_NOT_FOUND.getErrorCode());
		Mockito.verify(accountingService,Mockito.times(2)).creditAccount(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
		Mockito.verify(accountingService,Mockito.times(1)).debitAccount(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
	}
}
