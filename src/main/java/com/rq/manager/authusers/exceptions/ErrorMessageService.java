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
        messageSource.setBasename("error");
        messageSource.setDefaultEncoding("UTF-8");
    }

    /**
     * Gets the error message.
     *
     * @param errorKey the error key
     * @return the error message
     */
    public String getErrorMessage(String errorKey) {
        return messageSource.getMessage(errorKey + ".message", null, Locale.getDefault());
    }

    /**
     * Gets the error description.
     *
     * @param errorKey the error key
     * @return the error description
     */
    public String getErrorDescription(String errorKey) {
        return messageSource.getMessage(errorKey + ".description", null, Locale.getDefault());
    }

    /**
     * Gets the internal code.
     *
     * @param errorKey the error key
     * @return the internal code
     */
    public String getInternalCode(String errorKey) {
        return messageSource.getMessage(errorKey + ".internalCode", null, Locale.getDefault());
    }
}