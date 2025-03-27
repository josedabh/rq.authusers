package com.rq.manager.authusers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class ErrorResponse.
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
	
	/** The app name. */
	private String appName;
	
	/** The message. */
	private String message;
	
	/** The description. */
	private String description;
	
	/** The internal code. */
	private String internalCode;
	
	/** The status. */
	private int status;
}
