package com.rq.manager.authusers.exceptions;

import org.springframework.http.HttpStatus;

/**
 * The Class CustomException.
 */
public class CustomException extends AppException {
   
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new custom exception (HTTP 400 Bad Request).
	 *
	 * @param errorKey the error key
	 */
	public CustomException(String errorKey) {
        super(errorKey, HttpStatus.BAD_REQUEST);
    }
	
	/**
	 * Instantiates a new custom exception with cause.
	 *
	 * @param errorKey the error key
	 * @param cause the cause
	 */
	public CustomException(String errorKey, Throwable cause) {
		super(errorKey, HttpStatus.BAD_REQUEST);
		initCause(cause);
	}
}
