package com.rq.manager.authusers.enumerations;

/**
 * The Enum RolEnum.
 */
public enum RolEnum {
	
	/** The normal. */
	NORMAL("NORMAL"), 
	
	/** The admin. */
	ADMIN("ADMIN");

	/** The codigo. */
	private String codigo;

	/**
	 * Instantiates a new rol enum.
	 *
	 * @param codigo the codigo
	 */
	private RolEnum(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Gets the codigo.
	 *
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	
}
