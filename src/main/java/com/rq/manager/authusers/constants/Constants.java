package com.rq.manager.authusers.constants;

/**
 * The Class Constants.
 * Cambiar lo swager por ApiConstants
 */
public class Constants {
	
	/**
	 * Instantiates a new constants.
	 */
	private Constants() {
		// Default constructor
	}
	
	/** The Constant REQUEST_LOGIN. */
	public static final String REQUEST_LOGIN = "/api/v1/auth/login";
	
	/** The Constant REQUEST_REGISTER. */
	public static final String REQUEST_REGISTER = "/api/v1/auth/register";

	/** The Constant BEARER. */
	public static final String BEARER = "Bearer ";

	/** The Constant AUTHORIZATION. */
	public static final String AUTHORIZATION = "Authorization";
	
	/** The Constant UNAUTHORIZED. */
	public static final String UNAUTHORIZED = "Unauthorized";
	
	/** The Constant PENDING. */
	public static final String PENDING = "PENDIENTE";
	
	/** The Constant IN_PROGRESS. */
	public static final String IN_PROGRESS = "EN_PROGRESO";
	
	/** The Constant FINISHED. */
	public static final String FINISHED = "FINALIZADO";

	/** The Constant CANCELLED. */
	public static final String CANCELLED = "CANCELADO";
	
	/** The Constant FORMAT_VERIFICATION_CHALLENGE. */
	public static final String FORMAT_VERIFICATION_CHALLENGE = "%07d";
}
