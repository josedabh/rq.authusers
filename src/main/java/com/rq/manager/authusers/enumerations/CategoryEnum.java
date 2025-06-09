package com.rq.manager.authusers.enumerations;

/**
 * The Enum CategoryEnum.
 */
public enum CategoryEnum {

    /** The lectura. */
    LECTURA("LECTURA"),

    /** The deporte. */
    DEPORTE("DEPORTE"),

    /** The salud. */
    SALUD("SALUD"),

    /** The aprendizaje. */
    APRENDIZAJE("APRENDIZAJE"),

    /** The hobby. */
    HOBBY("HOBBY");

    /** The description. */
    private String description;

    /**
     * Instantiates a new category enum.
     *
     * @param description the description
     */
    private CategoryEnum(String description) {
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
     * @param description the description
     * @return the category enum
     */
    public static CategoryEnum setDescription(String description) {
        switch (description) {
            case "LECTURA":
                return LECTURA;
            case "DEPORTE":
                return DEPORTE;
            case "SALUD":
                return SALUD;
            case "APRENDIZAJE":
                return APRENDIZAJE;
            case "HOBBY":
                return HOBBY;
            default:
                throw new IllegalArgumentException("Unknown category: " + description);
        }
    }
}
