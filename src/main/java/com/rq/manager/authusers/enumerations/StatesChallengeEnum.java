package com.rq.manager.authusers.enumerations;

/**
 * The Enum StatesChallengeEnum.
 */
public enum StatesChallengeEnum {

	/** The cancelled. */
	CANCELLED("CANCELLED"),
	
	/** The pending. */
	PENDING("PENDING"),

	/** The in progress. */
	IN_PROGRESS("IN_PROGRESS"),

	/** The finished. */
	FINISHED("FINISHED");

	/** The state. */
	private String description;

	/**
	 * Instantiates a new states challenge.
	 *
	 * @param state the state
	 */
	private StatesChallengeEnum(String description) {
		this.description = description;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return description;
	}
	
	/**
	 * Sets the state.
	 *
	 * @param state the state
	 * @return the states challenge enum
	 */
	public static StatesChallengeEnum setState(String description) {
		switch (description) {
		case "CANCELLED":
			return CANCELLED;
		case "PENDING":
			return PENDING;
		case "IN_PROGRESS":
			return IN_PROGRESS;
		case "FINISHED":
			return FINISHED;
		default:
			throw new IllegalArgumentException("Unknown state: " + description);
		}
	}
}
