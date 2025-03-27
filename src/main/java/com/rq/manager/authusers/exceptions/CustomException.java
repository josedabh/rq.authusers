package com.rq.manager.authusers.exceptions;

/**
 * The Class CustomException.
 */
public class CustomException extends RuntimeException {
   
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new custom exception.
	 *
	 * @param errorKey the error key
	 */
	public CustomException(String errorKey) {
        super(errorKey);
    }
	
	/**
	 * Instantiates a new custom exception.
	 *
	 * @param cause the cause
	 */
	public CustomException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new custom exception.
	 *
	 * @param errorKey the error key
	 * @param cause the cause
	 */
	public CustomException(String errorKey, Throwable cause) {
		super(errorKey, cause);
	}
}
