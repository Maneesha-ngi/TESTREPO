package com.shafafiya.webapi.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.dao.DuplicateKeyException;

import com.shafafiya.webapi.service.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({SQLIntegrityConstraintViolationException.class, DuplicateKeyException.class})
	public ResponseEntity<ApiResponse> handlerSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
		
		ApiResponse response = ApiResponse.builder().message("Member already exist !").success(false).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		return new ResponseEntity<ApiResponse>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException e){

		ApiResponse response = ApiResponse.builder().message(e.getMessage()).success(false).status(HttpStatus.NOT_FOUND).build();
		return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ApiResponse> handlerNullPointerException(NullPointerException e){
	
		ApiResponse response = ApiResponse.builder().message(e.getMessage()).success(false).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		return new ResponseEntity<ApiResponse>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ApiResponse> handlerNoResourceFoundException(NoResourceFoundException e){

		ApiResponse response = ApiResponse.builder().message("No entry found with the details provided !!").success(false).status(HttpStatus.NOT_FOUND).build();
		return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(BadSqlGrammarException.class)
	public ResponseEntity<ApiResponse> GeneralException(BadSqlGrammarException e){

		e.printStackTrace();
		ApiResponse response = ApiResponse.builder().message("INTERNAL ERROR").success(false).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		return new ResponseEntity<ApiResponse>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> GeneralException(Exception e){

		e.printStackTrace();
		ApiResponse response = ApiResponse.builder().message(e.getMessage()).success(false).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		return new ResponseEntity<ApiResponse>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
}
