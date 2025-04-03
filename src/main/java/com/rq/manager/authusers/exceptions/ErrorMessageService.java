package com.rq.manager.authusers.exceptions;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * The Class ErrorMessageService.
 */
@Service
public class ErrorMessageService {

    /** The message source. */
    private final ResourceBundleMessageSource messageSource;

    /**
     * Instantiates a new error message service.
     */
    public ErrorMessageService() {
        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(ErrorConstants.ERROR_NAME);
        messageSource.setDefaultEncoding(ErrorConstants.UTF_8);
    }
    
    public String getErrorAppName() {
    	return messageSource.getMessage(ErrorConstants.APP_NAME, null, Locale.getDefault());
    }

    /**
     * Gets the error message.
     *
     * @param errorKey the error key
     * @return the error message
     */
    public String getErrorMessage(String errorKey) {
        return messageSource.getMessage(errorKey + ErrorConstants.MESSAGE, null, Locale.getDefault());
    }

    /**
     * Gets the error description.
     *
     * @param errorKey the error key
     * @return the error description
     */
    public String getErrorDescription(String errorKey) {
        return messageSource.getMessage(errorKey + ErrorConstants.DESCRIPTION, null, Locale.getDefault());
    }

    /**
     * Gets the internal code.
     *
     * @param errorKey the error key
     * @return the internal code
     */
    public String getInternalCode(String errorKey) {
        return messageSource.getMessage(errorKey + ErrorConstants.INTERNAL_CODE, null, Locale.getDefault());
    }
}