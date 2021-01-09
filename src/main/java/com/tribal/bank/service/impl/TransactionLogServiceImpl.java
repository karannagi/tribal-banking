package com.tribal.bank.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.tribal.bank.constants.TransactionStatus;
import com.tribal.bank.service.TransactionLogService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionLogServiceImpl implements TransactionLogService {

	@Override
	public void logTransferTransaction(Long customerId, String accountFrom, String accountTo, BigDecimal amount,
			String moneyTransferRemarks, TransactionStatus status, String referenceNumber, String rejectionReason) {
		log.info("logging transaction for customer: {} from account: {} to account: {} amount: {} remarks: {} status: {} referenceNo: {} rejected reason:{}"
				,customerId,accountFrom,accountTo,amount,moneyTransferRemarks,status,referenceNumber,rejectionReason);

	}

}
