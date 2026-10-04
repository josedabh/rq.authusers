package com.rq.manager.authusers.enumerations;

/**
 * Enum representing challenge difficulty levels.
 */
public enum DifficultyEnum {

    BEGINNER("BEGINNER"),
    INTERMEDIATE("INTERMEDIATE"),
    ADVANCED("ADVANCED"),
    EXPERT("EXPERT");

    private final String description;

    DifficultyEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns the enum constant for the given description (case-insensitive).
     *
     * @param description the difficulty string from the request
     * @return the matching DifficultyEnum
     * @throws IllegalArgumentException if the description is not recognised
     */
    public static DifficultyEnum fromDescription(String description) {
        if (description == null) {
            return null;
        }
        for (DifficultyEnum d : values()) {
            if (d.description.equalsIgnoreCase(description)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Unknown difficulty: " + description);
    }
}
