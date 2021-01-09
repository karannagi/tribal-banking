package com.tribal.bank.service;

import com.tribal.bank.exception.TribalBankingException;
import com.tribal.bank.request.dto.MoneyTransferRequestDTO;
import com.tribal.bank.response.dto.MoneyTransferResponseDTO;

public interface MoneyTransferService {

	public MoneyTransferResponseDTO moneyTransfer(String userName, MoneyTransferRequestDTO moneyTransferRequest) throws TribalBankingException;
}
