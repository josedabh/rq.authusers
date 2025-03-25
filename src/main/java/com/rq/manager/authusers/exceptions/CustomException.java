package com.rq.manager.authusers.exceptions;

public class CustomException extends RuntimeException {
   
	private static final long serialVersionUID = 1L;

	public CustomException(String errorKey) {
        super(errorKey);
    }
}
