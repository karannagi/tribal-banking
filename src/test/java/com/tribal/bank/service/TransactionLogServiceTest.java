package com.tribal.bank.service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tribal.bank.constants.TransactionStatus;
import com.tribal.bank.service.impl.TransactionLogServiceImpl;
@ExtendWith(MockitoExtension.class)
public class TransactionLogServiceTest {

	@InjectMocks
	TransactionLogServiceImpl transactionLogServiceImpl;
	
	@Test
	public void logTransferTransactionTest() {
		transactionLogServiceImpl.logTransferTransaction(1L, "1001", "1002", BigDecimal.ONE,
				"test", TransactionStatus.SUCCESS, "12345", "");
	}
}
