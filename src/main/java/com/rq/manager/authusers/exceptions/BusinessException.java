package com.rq.manager.authusers.exceptions;

public class BusinessException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new custom exception.
	 *
	 * @param errorKey the error key
	 */
	public BusinessException(String errorKey) {
		super(errorKey);
	}

	/**
	 * Instantiates a new custom exception.
	 *
	 * @param cause the cause
	 */
	public BusinessException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new custom exception.
	 *
	 * @param errorKey the error key
	 * @param cause    the cause
	 */
	public BusinessException(String errorKey, Throwable cause) {
		super(errorKey, cause);
	}
}