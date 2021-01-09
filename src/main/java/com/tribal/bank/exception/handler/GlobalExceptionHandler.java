package com.tribal.bank.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.tribal.bank.constants.MoneyTransferErrorCodes;
import com.tribal.bank.exception.TribalBankingException;
import com.tribal.bank.response.dto.ErrorResponseDTO;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler({ TribalBankingException.class})
	public final ResponseEntity<ErrorResponseDTO> handleException(TribalBankingException ex, WebRequest request) {
		log.error("Exception occured while processing request, errorCode: {}, errorDescription: {}",ex.getErrorCode(),ex.getMessage());

		return new ResponseEntity<ErrorResponseDTO>(
				ErrorResponseDTO.builder().errorCode(ex.getErrorCode()).errorDescription(ex.getMessage()).build(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler({ MethodArgumentNotValidException.class})
	public final ResponseEntity<ErrorResponseDTO> handleException(MethodArgumentNotValidException ex, WebRequest request) {

		return new ResponseEntity<ErrorResponseDTO>(
				ErrorResponseDTO.builder().errorCode(ex.getFieldError().getField()).errorDescription(ex.getFieldError().getDefaultMessage()).build(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler({ Exception.class})
	public final ResponseEntity<ErrorResponseDTO> handleException(Exception ex, WebRequest request) {

		return new ResponseEntity<ErrorResponseDTO>(
				ErrorResponseDTO.builder().errorCode(MoneyTransferErrorCodes.UNKOWN_ERROR.getErrorCode()).errorDescription(MoneyTransferErrorCodes.UNKOWN_ERROR.getErrorDescription()).build(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
