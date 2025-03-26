package com.rq.manager.authusers.bean;

import java.util.UUID;

import com.rq.manager.authusers.entity.Rol;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class UserResponse.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

	/** The id. */
	@Schema(description = "the uuid", example = "123e4567-e89b-12d3-a456-426655440000")
	private UUID id;
	
	/** The email. */
	@Schema(description = "the email", example = "example@exapmle.com")
	private String email;
	
	/** The password. */
	@Schema(description = "the password", example = "qwerty1234")
	private String password;
	
	/** The name. */
	@Schema(description = "the name", example = "mario")
	private String name;
	
	/** The lastname. */
	@Schema(description = "the lastname", example = "perez")
	private String lastname;
	
	/** The username. */
	@Schema(description = "the username", example = "mariop12")
	private String username;
	
	/** The num phone. */
	@Schema(description = "the number phone", example = "623456789")
	private String numPhone;
	
	/** The rol. */
	@Schema(description = "the rol", example = "normal")
	private Rol rol;
}
