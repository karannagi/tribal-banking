package com.tribal.bank.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
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
import com.tribal.bank.exception.TribalBankingException;
import com.tribal.bank.repo.AccountRepository;
import com.tribal.bank.response.dto.AccountingServiceResponse;
import com.tribal.bank.service.impl.AccountingServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AccountingServiceTest {

	@Mock
	private LedgerEntryService transactionLogService;
	
	@Mock
	private AccountRepository accountRepository;
	
	@InjectMocks
	private AccountingServiceImpl accountingService;
	
	private Account account;
	
	@BeforeEach
	public void init(){
		account = new Account();
		account.setBalance(BigDecimal.TEN);
		account.setActive(Boolean.TRUE);
	}
	
	@Test
	public void creditAccountSuccess() throws TribalBankingException {
		Mockito.when(accountRepository.findByAccountNumber(Mockito.anyString())).thenReturn(Optional.of(account));
		AccountingServiceResponse response = accountingService.creditAccount("123456", BigDecimal.ONE, "Test", "12345");
		assertNotNull(response);
	}
	
	@Test
	public void creditAccountAccNotFound() throws TribalBankingException {
		Mockito.doAnswer(i->{
			throw new TribalBankingException(MoneyTransferErrorCodes.CREDIT_ACCOUNT_IS_NOT_FOUND);
		}).when(accountRepository).findByAccountNumber(Mockito.anyString());
		try {
		accountingService.creditAccount("123456", BigDecimal.ONE, "Test", "12345");
		}
		catch(TribalBankingException e) {
			assertEquals(e.getMessage(),MoneyTransferErrorCodes.CREDIT_ACCOUNT_IS_NOT_FOUND.getErrorDescription());
			assertEquals(e.getErrorCode(),MoneyTransferErrorCodes.CREDIT_ACCOUNT_IS_NOT_FOUND.getErrorCode());
		}

	}
	
	@Test
	public void debitAccountSuccess() throws TribalBankingException {
		Mockito.when(accountRepository.findByAccountNumber(Mockito.anyString())).thenReturn(Optional.of(account));
		AccountingServiceResponse response = accountingService.debitAccount("123456", BigDecimal.ONE, "Test", "12345");
		assertNotNull(response);
	}
	
	@Test
	public void debitAccountOverDraft() throws TribalBankingException {
		Mockito.when(accountRepository.findByAccountNumber(Mockito.anyString())).thenReturn(Optional.of(account));
		try {
		accountingService.debitAccount("123456", new BigDecimal(12.3f), "Test", "12345");
		}
		catch(TribalBankingException e) {
			assertEquals(e.getMessage(),MoneyTransferErrorCodes.INSUFFICIENT_BALANCE.getErrorDescription());
			assertEquals(e.getErrorCode(),MoneyTransferErrorCodes.INSUFFICIENT_BALANCE.getErrorCode());
		}

	}

}
