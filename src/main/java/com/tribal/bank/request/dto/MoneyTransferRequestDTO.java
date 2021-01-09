package com.tribal.bank.request.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoneyTransferRequestDTO {

	@NotBlank(message="From account cannot be blank")
	private String accountFrom;
	@NotBlank(message="To account cannot be blank")
	private String accountTo;
	
	@DecimalMin(value = "0.0", inclusive = false,message="You cannot transfer the given amount")
	private BigDecimal amount;
	@NotBlank(message="Remarks cannot be blank")
	private String remarks;
}
