package com.rq.manager.authusers.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class Login.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@NotBlank
public class Login {

    @Size(max = 100, message = "El identificador no puede exceder los 100 caracteres")
    @Schema(description = "El identificador: email, teléfono o nombre de usuario", example = "usuario@example.com")
    private String identifier;
	
	/** The password. */
	@Size(min = 8, max = 50)
	@Schema(description = "the password", example = "pepito1234")
	private String password;
}
