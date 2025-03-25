package com.rq.manager.authusers.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rq.manager.authusers.bean.Login;
import com.rq.manager.authusers.bean.Register;
import com.rq.manager.authusers.entity.User;
import com.rq.manager.authusers.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * The Class AuthController.
 */
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Tag(name = "AuthController", description = "Controlador que maneja los usuarios")
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
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "400", description = "Error en la solicitud")
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody Register register) {
		try {
			User user = authService.registerUser(register);
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
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
//	@Operation(summary = "Logear al usuario", description = "Logear al usuario para que entre al sistema")
//    @ApiResponse(responseCode = "201", description = "Usuario logueado",
//            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
//    @ApiResponse(responseCode = "400", description = "Error en la solicitud")
//	@PostMapping("/login")
//	public ResponseEntity<?> loginUser (@Valid @RequestBody Login login){
//		try {
//			User user = authService.authenticateUser(login);
//			return ResponseEntity.status(HttpStatus.CREATED).body(user);
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//		}
//	}
}
