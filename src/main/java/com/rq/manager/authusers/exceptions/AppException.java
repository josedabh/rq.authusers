package com.rq.manager.authusers.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Base application exception. All domain exceptions extend this class.
 * The error key is the message bundle key used for i18n lookup.
 */
public abstract class AppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** HTTP status to return in the response. */
    private final HttpStatus status;

    /**
     * Instantiates a new app exception.
     *
     * @param errorKey the message bundle key
     * @param status   the HTTP status code
     */
    protected AppException(String errorKey, HttpStatus status) {
        super(errorKey);
        this.status = status;
    }

    /**
     * Gets the HTTP status.
     *
     * @return the status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * Gets the error key (same as getMessage()).
     *
     * @return the error key
     */
    public String getErrorKey() {
        return getMessage();
    }
}
