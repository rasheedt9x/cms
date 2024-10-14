package com.sgdc.cms.exceptions;


public class ApplicationSaveException extends RuntimeException{
	public ApplicationSaveException(String message, Throwable cause){
	    super(message,cause);
	}
}
