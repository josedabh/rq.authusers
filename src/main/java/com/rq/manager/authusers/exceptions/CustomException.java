package com.rq.manager.authusers.exceptions;

public class CustomException extends RuntimeException {
   
	private static final long serialVersionUID = 1L;

	public CustomException(String errorKey) {
        super(errorKey);
    }
	
	public CustomException(Throwable cause) {
		super(cause);
	}
	
	public CustomException(String errorKey, Throwable cause) {
		super(errorKey, cause);
	}
}
