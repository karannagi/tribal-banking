package com.tribal.bank.helper;

import java.util.Objects;

import com.tribal.bank.constants.MoneyTransferErrorCodes;
import com.tribal.bank.entity.Account;
import com.tribal.bank.entity.Customer;
import com.tribal.bank.exception.TribalBankingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerValidationHelper {

	private CustomerValidationHelper(){}

	public static boolean accountBelongsToUser(final Customer customer, final String accountNumber) throws TribalBankingException {
		if(customer.getActive().equals(Boolean.TRUE)) {
			if(!customer.getAccounts().isEmpty()) {
				if(customer.getAccounts().stream().filter(x->x.getAccountNumber().equals(accountNumber)).findAny().isPresent()) {
					log.info("Account number verification with customer is successful");
					return true;
				}
					
				else
					throw new TribalBankingException(MoneyTransferErrorCodes.ACCOUNT_DOES_NOT_BELONG_TO_USER);

			}else {
				throw new TribalBankingException(MoneyTransferErrorCodes.CUSTOMER_HAS_NO_ACCOUNT);
			}
		}else {
			throw new TribalBankingException(MoneyTransferErrorCodes.CUSTOMER_IS_NOT_ACTIVE);
		} 


	}

	public static void checkIfAccountIsActive(final Account account,final Boolean creditAccount) throws TribalBankingException {
		if(!Objects.nonNull(creditAccount) && !account.getActive())
			throw new TribalBankingException(MoneyTransferErrorCodes.ACCOUNT_IS_NOT_ACTIVE);
		if(account.getActive().equals(Boolean.FALSE)) {
			if(creditAccount)
				throw new TribalBankingException(MoneyTransferErrorCodes.CREDIT_ACCOUNT_IS_NOT_ACTIVE);
			else
				throw new TribalBankingException(MoneyTransferErrorCodes.DEBIT_ACCOUNT_IS_NOT_ACTIVE);	
		}
		log.info("The account active check is succesful");
	}
}
