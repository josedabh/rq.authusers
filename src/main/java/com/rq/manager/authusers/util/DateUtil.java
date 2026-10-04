package com.rq.manager.authusers.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import com.rq.manager.authusers.exceptions.CustomException;

/**
 * Utility for safe LocalDateTime parsing and formatting.
 * Wraps parsing errors into domain exceptions to avoid 500s from bad input.
 */
public class DateUtil {

    private DateUtil() {
        // Utility class — no instantiation
    }

    /**
     * Safely parses an ISO 8601 date-time string into a LocalDateTime.
     * Returns null when the input is blank.
     * Throws {@link CustomException} with key "invalid_date_format" on parse failure.
     *
     * @param date ISO 8601 date-time string (e.g. "2024-01-15T10:30:00")
     * @return the parsed LocalDateTime, or null if input is blank
     */
    public static LocalDateTime parse(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }
        try {
            return LocalDateTime.parse(date);
        } catch (DateTimeParseException e) {
            throw new CustomException("invalid_date_format");
        }
    }

    /**
     * Formats a LocalDateTime to its ISO 8601 string representation.
     *
     * @param date the date to format (may be null)
     * @return the ISO 8601 string, or null if input is null
     */
    public static String format(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.toString();
    }
}
