package com.rq.manager.authusers.exceptions;

/**
 * The Class CustomException.
 */
public class ResourceNotFoundException extends RuntimeException {
   
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new custom exception.
	 *
	 * @param errorKey the error key
	 */
	public ResourceNotFoundException(String errorKey) {
        super(errorKey);
    }
	
	/**
	 * Instantiates a new custom exception.
	 *
	 * @param cause the cause
	 */
	public ResourceNotFoundException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new custom exception.
	 *
	 * @param errorKey the error key
	 * @param cause the cause
	 */
	public ResourceNotFoundException(String errorKey, Throwable cause) {
		super(errorKey, cause);
	}
}
