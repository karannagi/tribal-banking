package com.tribal.bank.exception;

import org.apache.commons.lang3.StringUtils;

import com.tribal.bank.constants.MoneyTransferErrorCodes;

import lombok.Getter;

@Getter
public class TribalBankingException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String errorCode;
	
	public TribalBankingException(String errorCode,String errorMessage){
		super(errorMessage);
		this.errorCode=errorCode;
	}
	
	public TribalBankingException() {
		super();
		this.errorCode = StringUtils.EMPTY;
	}

	public TribalBankingException(MoneyTransferErrorCodes moneyTransferErrorCode) {
		super(moneyTransferErrorCode.getErrorDescription());
		this.errorCode = moneyTransferErrorCode.getErrorCode();
	}

}
