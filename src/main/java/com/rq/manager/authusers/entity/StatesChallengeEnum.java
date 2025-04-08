package com.rq.manager.authusers.entity;

/**
 * The Enum StatesChallengeEnum.
 */
public enum StatesChallengeEnum {

	/** The cancelled. */
	CANCELLED("Cancelled"),
	
	/** The pending. */
	PENDING("Pending"),

	/** The in progress. */
	IN_PROGRESS("In_progress"),

	/** The finished. */
	FINISHED("Finished");

	/** The state. */
	private String state;

	/**
	 * Instantiates a new states challenge.
	 *
	 * @param state the state
	 */
	private StatesChallengeEnum(String state) {
		this.state = state;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}
}
