package com.rq.manager.authusers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The Class GlobalExceptionHandler.
 * No se puede sobrescribir excepcions que ya existen
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/** The error message service. */
	private final ErrorMessageService errorMessageService;

	/**
	 * Instantiates a new global exception handler.
	 *
	 * @param errorMessageService the error message service
	 */
	public GlobalExceptionHandler(ErrorMessageService errorMessageService) {
		this.errorMessageService = errorMessageService;
	}

	/**
	 * Handle null pointer exception.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException ex) {
		return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle illegal argument exception.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle custom exception.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
		return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Builds the error response.
	 *
	 * @param errorKey the error key
	 * @param status the status
	 * @return the response entity
	 */
	private ResponseEntity<ErrorResponse> buildErrorResponse(String errorKey, HttpStatus status) {
		ErrorResponse errorResponse = new ErrorResponse(errorMessageService.getErrorMessage(errorKey),
				errorMessageService.getErrorDescription(errorKey), errorMessageService.getInternalCode(errorKey),
				status.value());
		return new ResponseEntity<>(errorResponse, status);
	}
}
