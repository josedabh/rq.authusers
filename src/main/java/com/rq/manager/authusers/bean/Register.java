package com.rq.manager.authusers.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class Register.
 */
@Getter
@Setter
@NoArgsConstructor
public class Register {

	/** The email. */
	@Size(max = 100)
	@Email
	@NotBlank
	@Schema(description = "the email", example = "example@example.com")
	private String email;
	
	/** The password. */
	@NotBlank
	@Size(min = 8, max = 50)
	@Schema(description = "the password", example = "example1234")
	private String password;
	
	/** The name. */
	@Size(max = 80)
	@Schema(description = "the name", example = "example")
	private String name;
	
	/** The lastname. */
	@Size(max = 150)
	@Schema(description = "the lastname", example = "perez")
	private String lastname;
	
	/** The username. */
	@NotBlank
	@Size(max = 100)
	@Schema(description = "the username", example = "exampleperez")
	private String username;
	
	@Size(min = 9, max = 9)
	@Pattern(regexp = "\\d{9}")
	@Schema(description = "the number phone", example = "612345678")
	private String numPhone;
	
}
