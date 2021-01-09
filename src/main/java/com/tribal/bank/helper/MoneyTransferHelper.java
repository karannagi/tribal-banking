package com.tribal.bank.helper;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MoneyTransferHelper {
	private static SecureRandom random = new SecureRandom();

	private static final DateTimeFormatter sdf =  DateTimeFormatter.ofPattern("MMddHHss");
	private MoneyTransferHelper() {}


	public static String createDebitRemarks(final String fromAccount,final String toAccount) {
		StringBuilder sb = new StringBuilder();

		return sb.append(fromAccount).append(":").append(toAccount).toString();
	}

	public static String createCreditRemarks(final String fromAccount,final String toAccount) {
		StringBuilder sb = new StringBuilder();
		return sb.append(fromAccount).append(":").append(toAccount).toString();
	}


	public static String createMoneyTransferRemarks(String accountFrom, String accountTo, String remarks) {
		StringBuilder sb = new StringBuilder();
		return sb.append(accountFrom).append(":").append(accountTo).append(" ").append(remarks).toString();
	}

	public static String generateReferenceNumber() {
		StringBuilder sb = new StringBuilder();
		return sb.append(sdf.format(LocalDateTime.now())).append(random.nextInt(9999)).toString();

	}


	public static String createReversalRemarks(String accountTo) {
		StringBuilder sb = new StringBuilder();
		return sb.append("Amount reversed for failed transaction").append(accountTo).toString();
	}
}
