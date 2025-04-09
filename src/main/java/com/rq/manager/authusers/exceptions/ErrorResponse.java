package com.rq.manager.authusers.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class ErrorResponse.
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
	
	/** The app name. */
	@Schema(description = "Name of the application", example = "RQ Manager")
	private String appName;
	
	/** The message. */
	@Schema(description = "Error message", example = "User not found")
	private String message;
	
	/** The description. */
	@Schema(description = "Error description", example = "User with ID 123 not found in the database")
	private String description;
	
	/** The internal code. */
	@Schema(description = "Internal error code", example = "ERR-123")
	private String internalCode;
	
	/** The status. */
	@Schema(description = "HTTP status code", example = "404")
	private int status;
}
