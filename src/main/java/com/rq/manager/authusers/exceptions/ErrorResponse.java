package com.rq.manager.authusers.exceptions;

import java.time.Instant;

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

	/** The timestamp when the error occurred. */
	@Schema(description = "Timestamp of the error", example = "2024-01-15T10:30:00Z")
	private Instant timestamp;

	/** The request path that triggered the error. */
	@Schema(description = "Request path", example = "/api/v1/challenge/123")
	private String path;

	/** The locale used for the error message. */
	@Schema(description = "Locale used for the message", example = "es")
	private String locale;
}

