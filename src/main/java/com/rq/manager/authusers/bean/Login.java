package com.rq.manager.authusers.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@NotBlank
public class Login {

	/** The email. */
	@Size(max = 100)
	@Email
	@Schema(description = "the email", example = "example@example.com")
	private String email;
	
	@Size(min = 8, max = 50)
	@Schema(description = "the password", example = "pepito1234")
	private String password;
}
