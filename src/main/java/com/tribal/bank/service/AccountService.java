package com.tribal.bank.service;

import com.tribal.bank.exception.TribalBankingException;
import com.tribal.bank.response.dto.AccountDetailsDTO;

public interface AccountService {
	public AccountDetailsDTO accountDetails(String userName,String accountNumber) throws TribalBankingException;

}
