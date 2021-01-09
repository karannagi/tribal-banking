package com.tribal.bank.constants;

import lombok.Getter;

@Getter
public enum MoneyTransferErrorCodes {

	ACCOUNT_DOES_NOT_BELONG_TO_USER("MT-ERR-001","The account does not belong to user"),
	CUSTOMER_HAS_NO_ACCOUNT("MT-ERR-002","The customer doesn't have any account"),
	CUSTOMER_IS_NOT_ACTIVE("MT-ERR-003","The customer is not active"),
	DEBIT_ACCOUNT_IS_NOT_ACTIVE("MT-ERR-004","The debit account is not active"),
	ACCOUNT_IS_NOT_ACTIVE("MT-ERR-008","The account is not active"),
	CREDIT_ACCOUNT_IS_NOT_ACTIVE("MT-ERR-005","The credit account is not active"),
	CREDIT_ACCOUNT_IS_NOT_FOUND("MT-ERR-006","The credit account is not found"),
	DEBIT_ACCOUNT_IS_NOT_FOUND("MT-ERR-007","The debit account is not found"),
	ACCOUNT_IS_NOT_FOUND("MT-ERR-009","The account is not found"),
	INSUFFICIENT_BALANCE("MT-ERR-008","Insufficient balance for transaction"),
	CUSTOMER_NOT_FOUND("MT-ERR-009","Customer not found"),
	UNKOWN_ERROR("MT-ERR-100","Unkown error has occured, check logs"), 
	;

	private final String errorDescription;
	private final String errorCode;
	
	MoneyTransferErrorCodes(String errorCode,String errorDescription) {
		this.errorCode=errorCode;
		this.errorDescription=errorDescription;
	}
}
