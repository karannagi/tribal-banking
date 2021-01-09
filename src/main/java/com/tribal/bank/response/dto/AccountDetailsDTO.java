package com.tribal.bank.response.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountDetailsDTO {

	private BigDecimal balance;
	private String accountType;
	private String branch;
}
