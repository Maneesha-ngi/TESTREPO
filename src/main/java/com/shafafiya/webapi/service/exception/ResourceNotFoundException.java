package com.shafafiya.webapi.service.exception;

public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException() {
		super("Resource Not found on server !!");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}

}
