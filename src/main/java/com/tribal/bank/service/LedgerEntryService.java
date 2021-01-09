package com.tribal.bank.service;

import java.math.BigDecimal;

public interface LedgerEntryService {

	public void logLedgerEntry(String account, BigDecimal amount, String remarks,String transactionReference);
}
