package com.tribal.bank.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.Principal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tribal.bank.exception.TribalBankingException;
import com.tribal.bank.request.dto.MoneyTransferRequestDTO;
import com.tribal.bank.response.dto.AccountDetailsDTO;
import com.tribal.bank.response.dto.MoneyTransferResponseDTO;
import com.tribal.bank.service.AccountService;
import com.tribal.bank.service.MoneyTransferService;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {
	@Mock
	private MoneyTransferService moneyTransferService;
	@Mock
	private AccountService accountService;
	
	@InjectMocks
	private AccountController accountController;
	
	@Test
	public void transferTest() throws TribalBankingException {
		Mockito.when(moneyTransferService.moneyTransfer(Mockito.any(), Mockito.any())).thenReturn(MoneyTransferResponseDTO.builder().referenceNumber("123456").build());
		MoneyTransferResponseDTO response = accountController.transfer(new MoneyTransferRequestDTO(), new Principal() {
			
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "testUser";
			}
		});
		assertNotNull(response);
		assertEquals(response.getReferenceNumber(),"123456");
		
		
	}
	
	@Test
	public void getAccountDetailsTest() throws TribalBankingException {
		Mockito.when(accountService.accountDetails(Mockito.any(), Mockito.any())).thenReturn(new AccountDetailsDTO());
		AccountDetailsDTO response = accountController.getAccountDetails("123456", new Principal() {
			
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "testUser";
			}
		});
		assertNotNull(response);
	}
}
