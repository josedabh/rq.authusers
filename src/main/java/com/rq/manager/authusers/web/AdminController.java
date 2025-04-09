package com.rq.manager.authusers.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rq.manager.authusers.bean.UserResponse;
import com.rq.manager.authusers.constants.Constants;
import com.rq.manager.authusers.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

/**
 * The Class AdminController.
 */
@RestController
@RequestMapping("/api/admin/auth")
@AllArgsConstructor
@Tag(name = "AdminController",
description = "Los administradores realizan acciones administrativas")
@ApiResponses(value = { 
		@ApiResponse(responseCode = "400", description = "BAD REQUEST") 
})
public class AdminController {

	/** The admin service. */
	private AdminService adminService;

	/**
	 * List users.
	 *
	 * @return the list users
	 */
	@Operation(summary = "Lista los usuarios registrados", 
			description = "Lista los usuarios registrados en la base de datos")
	@ApiResponse(responseCode = "200", description = "Lista de usuarios",
		content = @Content(mediaType = Constants.APPLICATION_JSON,
		schema = @Schema(implementation = UserResponse.class)))
	@GetMapping("/list-users")
	public List<UserResponse> listUsers() {
		return adminService.getListUsers();
	}

}
