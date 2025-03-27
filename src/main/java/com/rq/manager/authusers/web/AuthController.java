package com.rq.manager.authusers.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rq.manager.authusers.bean.Login;
import com.rq.manager.authusers.bean.Register;
import com.rq.manager.authusers.bean.UserResponse;
import com.rq.manager.authusers.constants.Constants;
import com.rq.manager.authusers.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * The Class AuthController.
 */
@RestController
@RequestMapping("/api/user/auth")
@AllArgsConstructor
@Tag(name = "AuthController", 
description = "Controlador donde se genera un token")
@ApiResponses(value = {
		@ApiResponse(responseCode = "400", description = Constants.BAD_REQUEST),
		@ApiResponse(responseCode = "401", description = Constants.BAD_REQUEST)
})
public class AuthController {

	/** The auth service. */
	private AuthService authService;

	/**
	 * Register user.
	 *
	 * @param register the register
	 * @return the response entity
	 */
	@Operation(summary = "Registrar un nuevo usuario", description = "Registra un usuario con rol NORMAL.")
    @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente",
            content = @Content(mediaType = Constants.MEDIA_TYPE, schema = @Schema(implementation = UserResponse.class)))
	@PostMapping("/register")
	public UserResponse registerUser(@Valid @RequestBody Register register) {
		return authService.registerUser(register);
	}
	
	/**
	 * Gets the hello. The evidence if works swagger
	 *
	 * @return the hello
	 */
	@GetMapping()
	public String getHello() {
		return "Hola";
	}
	
	/**
	 * Modificar este controller cuando sepa como hacerlo bien
	 * Login user.
	 *
	 * @param login the login
	 * @return the response entity
	 */
	@PostMapping("/login")
    public String login(@Valid @RequestBody Login login) {
        return authService.authenticateUser(login);
    }
	
}
