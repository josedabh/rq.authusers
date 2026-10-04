package com.rq.manager.authusers.exceptions;

import java.time.Instant;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

/**
 * The Class GlobalExceptionHandler.
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
	 * Handle any AppException subclass using its own HTTP status and error key.
	 *
	 * @param ex      the exception
	 * @param request the HTTP request
	 * @return the response entity
	 */
	@ExceptionHandler(AppException.class)
	public ResponseEntity<ErrorResponse> handleAppException(AppException ex, HttpServletRequest request) {
		Locale locale = resolveLocale(request);
		return buildErrorResponse(ex.getErrorKey(), ex.getStatus(), request.getRequestURI(), locale);
	}

	/**
	 * Handle bean validation errors (@Valid on @RequestBody).
	 *
	 * @param ex      the exception
	 * @param request the HTTP request
	 * @return the response entity
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		Locale locale = resolveLocale(request);
		String fieldErrors = ex.getBindingResult().getFieldErrors().stream()
				.map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
				.collect(Collectors.joining("; "));
		ErrorResponse errorResponse = new ErrorResponse(
				errorMessageService.getErrorAppName(),
				fieldErrors,
				ErrorConstants.VALIDATION_FAILED_DESCRIPTION,
				ErrorConstants.VALIDATION_FAILED_CODE,
				HttpStatus.BAD_REQUEST.value(),
				Instant.now(),
				request.getRequestURI(),
				locale.getLanguage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Resolve locale from Accept-Language header, falling back to context default.
	 *
	 * @param request the HTTP request
	 * @return the resolved locale
	 */
	private Locale resolveLocale(HttpServletRequest request) {
		String acceptLanguage = request.getHeader("Accept-Language");
		if (acceptLanguage != null && !acceptLanguage.isBlank()) {
			try {
				return Locale.forLanguageTag(acceptLanguage.split(",")[0].trim());
			} catch (Exception e) {
				// fall through to default
			}
		}
		return LocaleContextHolder.getLocale();
	}

	/**
	 * Builds the error response.
	 *
	 * @param errorKey the error key
	 * @param status   the HTTP status
	 * @param path     the request path
	 * @param locale   the resolved locale
	 * @return the response entity
	 */
	private ResponseEntity<ErrorResponse> buildErrorResponse(String errorKey, HttpStatus status, String path,
			Locale locale) {
		ErrorResponse errorResponse = new ErrorResponse(
				errorMessageService.getErrorAppName(),
				errorMessageService.getErrorMessage(errorKey, locale),
				errorMessageService.getErrorDescription(errorKey, locale),
				errorMessageService.getInternalCode(errorKey),
				status.value(),
				Instant.now(),
				path,
				locale.getLanguage());
		return new ResponseEntity<>(errorResponse, status);
	}
}
