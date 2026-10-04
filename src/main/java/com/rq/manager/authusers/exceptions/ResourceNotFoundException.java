package com.rq.manager.authusers.exceptions;

import org.springframework.http.HttpStatus;

/**
 * The Class ResourceNotFoundException.
 */
public class ResourceNotFoundException extends AppException {
   
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new resource not found exception (HTTP 404).
	 *
	 * @param errorKey the error key
	 */
	public ResourceNotFoundException(String errorKey) {
        super(errorKey, HttpStatus.NOT_FOUND);
    }
	
	/**
	 * Instantiates a new resource not found exception with cause.
	 *
	 * @param errorKey the error key
	 * @param cause the cause
	 */
	public ResourceNotFoundException(String errorKey, Throwable cause) {
		super(errorKey, HttpStatus.NOT_FOUND);
		initCause(cause);
	}
}
