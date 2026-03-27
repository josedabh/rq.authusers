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
public class Login {

    /** The identifier. */
    @Size(max = 100)
    @NotBlank
    @Schema(description = "the identifier: email, number phone or username", example = "exampleperez")
    private String identifier;
	
	/** The password. */
	@Size(min = 8, max = 50)
	@NotBlank
	@Schema(description = "the password", example = "example1234")
	private String password;
}
