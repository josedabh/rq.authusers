package com.rq.manager.authusers.util;

import java.time.LocalDateTime;

/**
 * The Class Util.
 */
public class Util {
	
	/**
	 * Instantiates a new util.
	 */
	public Util() {
		// Default constructor
	}
	
	/**
	 * Gets the state.
	 *
	 * @param state the state
	 * @return the state
	 */
	public static String getState(String state) {
		switch (state) {
		case "ACTIVE":
			return "ACTIVE";
		case "INACTIVE":
			return "INACTIVE";
		case "DISABLED":
			return "DISABLED";
		default:
			return null;
		}
	}
	
	/**
	 * Gets the local date time.
	 *
	 * @param date the date
	 * @return the local date time
	 */
	public static LocalDateTime getLocalDateTime(String date) {
		return LocalDateTime.parse(date);
	}
	
	/**
	 * Gets the date.
	 *
	 * @param date the date
	 * @return the date
	 */
	public static String getDate(LocalDateTime date) {
		return date.toString();
	}

}
