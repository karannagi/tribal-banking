package com.tribal.bank.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tribal.bank.exception.TribalBankingException;
import com.tribal.bank.request.dto.MoneyTransferRequestDTO;
import com.tribal.bank.response.dto.AccountDetailsDTO;
import com.tribal.bank.response.dto.MoneyTransferResponseDTO;
import com.tribal.bank.service.AccountService;
import com.tribal.bank.service.MoneyTransferService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/account")
public class AccountController {

	private final MoneyTransferService moneyTransferService;
	private final AccountService accountService;

	/**
	 * Entry point for funds transfers service
	 * @param moneyTransferRequest - coming from front end
	 * @param principal - this is being injected by spring security after validating the basic username password
	 * @return
	 * @throws TribalBankingException
	 */
	@PostMapping("/transfer")
	@ApiOperation(value="Money Transfer API",notes="This API is used to transfer money between two accounts")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "The request has succeeded or (your message)"),
	        @ApiResponse(code = 401, message = "The request requires user authentication or (your message)"),
	        @ApiResponse(code = 400, message = "The request which you've sent isn't correct")})
	public MoneyTransferResponseDTO transfer(@RequestBody @Valid MoneyTransferRequestDTO moneyTransferRequest,Principal principal) throws TribalBankingException {
		log.info("Received money transfer request for {}",principal.getName());
		MoneyTransferResponseDTO response = moneyTransferService.moneyTransfer(principal.getName(), moneyTransferRequest);
		log.info("Completed money transfer request for {}",principal.getName());
		return response;
	}
	/**
	 * Entry point for getting account information
	 * @param accountNumber - coming from front end
	 * @param principal- this is being injected by spring security after validating the basic username password
	 * @return
	 * @throws TribalBankingException
	 */
	@GetMapping("/{accountNumber}")
	@ApiOperation(value="Account Details",notes="This API is used to get you account information")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "The request has succeeded or (your message)"),
	        @ApiResponse(code = 401, message = "The request requires user authentication or (your message)"),
	        @ApiResponse(code = 400, message = "The request which you've sent isn't correct")})
	public AccountDetailsDTO getAccountDetails(@PathVariable("accountNumber") String accountNumber,Principal principal) throws TribalBankingException{
		log.info("Received account details request for {}",principal.getName());
		AccountDetailsDTO response =  accountService.accountDetails(principal.getName(), accountNumber);
		log.info("Completed account details request for {}",principal.getName());
		return response;
	}
}
