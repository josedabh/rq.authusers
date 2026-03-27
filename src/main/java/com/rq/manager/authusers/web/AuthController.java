package com.rq.manager.authusers.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rq.manager.authusers.bean.Credentials;
import com.rq.manager.authusers.bean.FormPassword;
import com.rq.manager.authusers.bean.Login;
import com.rq.manager.authusers.bean.Register;
import com.rq.manager.authusers.bean.UpdateUserInfoRequest;
import com.rq.manager.authusers.bean.admin.UserResponse;
import com.rq.manager.authusers.constants.ApiConstants;
import com.rq.manager.authusers.constants.Constants;
import com.rq.manager.authusers.exceptions.ErrorResponse;
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
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Tag(name = "Auth Controller", 
description = "Controlador donde se genera un token")
@ApiResponses(value = {
		@ApiResponse(responseCode = "400", 
			description = ApiConstants.BAD_REQUEST, 
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "401", 
			description = Constants.UNAUTHORIZED, 
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
			schema = @Schema(implementation = ErrorResponse.class))) 
})
public class AuthController {

	/** The auth service. */
	private AuthService authService;
	
	//Prueba para ver si funciona la conexion
	@GetMapping("/hello")
	public String hello() {
		return "Hello world!";
	}

	/**
	 * Register user.
	 *
	 * @param register the register
	 * @return the user response
	 */
	@Operation(summary = "Registrar un nuevo usuario", 
			description = "Registra un usuario con rol NORMAL.")
    @ApiResponse(responseCode = "200", 
    		description = "Usuario registrado exitosamente",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Credentials.class)))
	@PostMapping("/register")
	public Credentials registerUser(@Valid @RequestBody Register register) {
		return authService.registerUser(register);
	}
	
	/**
	 * Login the user.
	 *
	 * @param login the login
	 * @return the token
	 */
	@Operation(summary = "Iniciar sesión del usuario",
			description = "El usuario puede iniciar sesión con su email,"
					+ " nombre de usuario o número de teléfono.")
    @ApiResponse(responseCode = "200", description = "Iniciar sesión exitosamente",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Credentials.class)))
	@PostMapping("/login")
    public Credentials login(@Valid @RequestBody Login login) {
        return authService.authenticateUser(login);
    }
	
	/**
	 * Info user.
	 *
	 * @return the user response
	 */
	@Operation(summary = "Obtener información del usuario", 
		description = "El usuario puede obtener su información.")
	@ApiResponse(responseCode = "200", 
		description = "Obtener información del usuario exitosamente",
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = UserResponse.class)))
	@GetMapping("/info-user")
	public UserResponse infoUser() {
		return authService.getUser();
	}
	
	/**
	 * Cmabiar el metodo para una eficiencia mayor
	 */
	@Operation(summary = "Cerrar sesión del usuario", 
			description = "El usuario puede cerrar sesión.")
	@ApiResponse(responseCode = "200", 
		description = "Cerrar sesión exitosamente", 
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
		schema = @Schema(implementation = Credentials.class)))
    @PostMapping("/logout")
    public void logout() {
        authService.logout();
    }

    /**
     * Change password.
     *
     * @param formPassword
     *            the form password
     * @return the user response
     */
    @Operation(summary = "Cambia la contraseña",
            description = "El usaurio puede cambiar la contraseña")
    @ApiResponse(responseCode = "200", 
	        description = "Contraseña cambiada", 
	        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
	        schema = @Schema(implementation = Void.class)))
	@PutMapping("/change-password")
	public void changePassword(@Valid @RequestBody FormPassword formPassword) {
	    authService.changePassword(formPassword);
	}
    
    /**
     * Update my info.
     *
     * @param req the req
     * @return the user response
     */
    @PatchMapping("/info-user")
    @Operation(
        summary = "Actualizar datos del usuario",
        description = "Permite al usuario autenticado modificar su email, nombre, apellido, username y teléfono"
    )
    @ApiResponse(responseCode = "200", description = "Usuario actualizado",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                           schema = @Schema(implementation = UserResponse.class)))
    public UserResponse updateMyInfo(
            @Valid @RequestBody UpdateUserInfoRequest req) {
        return authService.updateMyInfo(req);
    }
}
