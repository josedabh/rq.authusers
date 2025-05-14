package com.rq.manager.authusers.enumerations;

import com.rq.manager.authusers.constants.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Enum StatesChallengeEnum.
 */
public enum StatesChallengeEnum {

	/** The cancelled. */
	CANCELLED("CANCELADO"),
	
	/** The pending. */
	PENDING("PENDIENTE"),

	/** The in progress. */
	IN_PROGRESS("EN_PROGRESO"),

	/** The finished. */
	FINISHED("FINALIZADO");

	/** The description. */
	private String description;

	
	/**
	 * Instantiates a new states challenge enum.
	 *
	 * @param description the description
	 */
	private StatesChallengeEnum(String description) {
		this.description = description;
	}

    /**
     * Gets the description.
     *
     * @return the description
     */
	public String getDescription() {
		return description;
	}
	
    /**
     * Sets the description.
     *
     * @param description
     *            the description
     * @return the states challenge enum
     */
	public static StatesChallengeEnum setDescription(String description) {
		switch (description) {
		case Constants.CANCELLED:
			return CANCELLED;
		case Constants.PENDING:
			return PENDING;
		case Constants.IN_PROGRESS:
			return IN_PROGRESS;
		case Constants.FINISHED:
			return FINISHED;
		default:
			throw new IllegalArgumentException("Unknown state: " + description);
		}
	}
}
