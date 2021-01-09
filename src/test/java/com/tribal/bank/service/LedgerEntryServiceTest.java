package com.tribal.bank.service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tribal.bank.service.impl.LedgerEntryServiceImpl;

@ExtendWith(MockitoExtension.class)
public class LedgerEntryServiceTest {
	@InjectMocks
	LedgerEntryServiceImpl ledgerEntryService;
	
	@Test
	public void logLedgerEntryTest() {
		ledgerEntryService.logLedgerEntry("12345", BigDecimal.ONE, "test", "test");
	}
}
