package com.rq.manager.authusers.exceptions;

import org.springframework.http.HttpStatus;

public class BusinessException extends AppException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new business exception (HTTP 422 Unprocessable Entity).
	 *
	 * @param errorKey the error key
	 */
	public BusinessException(String errorKey) {
		super(errorKey, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	/**
	 * Instantiates a new business exception with cause.
	 *
	 * @param errorKey the error key
	 * @param cause    the cause
	 */
	public BusinessException(String errorKey, Throwable cause) {
		super(errorKey, HttpStatus.UNPROCESSABLE_ENTITY);
		initCause(cause);
	}
}
