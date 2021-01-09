package com.tribal.bank.service;

import java.math.BigDecimal;

import com.tribal.bank.exception.TribalBankingException;
import com.tribal.bank.response.dto.AccountingServiceResponse;

public interface AccountingService {

	public AccountingServiceResponse creditAccount( String accountNumber, BigDecimal amount,String remarks, String referenceNumber) throws TribalBankingException;
	public AccountingServiceResponse debitAccount( String accountNumber,BigDecimal amount, String remarks, String referenceNumber) throws TribalBankingException;
}
