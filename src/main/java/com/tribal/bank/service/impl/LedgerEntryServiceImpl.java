package com.tribal.bank.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.tribal.bank.service.LedgerEntryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LedgerEntryServiceImpl implements LedgerEntryService {

	@Override
	public void logLedgerEntry(String account, BigDecimal amount, String remarks, String transactionReference) {
		log.info("logging ledger {} {} {} {}",account,amount,remarks,transactionReference);

	}

}
