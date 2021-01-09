package com.tribal.bank.service;

import java.math.BigDecimal;

import com.tribal.bank.constants.TransactionStatus;

public interface TransactionLogService {

	public void logTransferTransaction(Long customerId, String accountFrom, String accountTo, BigDecimal amount, String moneyTransferRemarks, TransactionStatus status,String referenceNumber, String rejectionReason);
}
