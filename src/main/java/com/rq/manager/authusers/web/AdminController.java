package com.rq.manager.authusers.web;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rq.manager.authusers.bean.ChallengeRequest;
import com.rq.manager.authusers.bean.ChallengeResponse;
import com.rq.manager.authusers.bean.UserResponse;
import com.rq.manager.authusers.service.AdminService;

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
@Tag(name = "AuthController", 
description = "Controlador que maneja los usuarios")
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
	@GetMapping("/list-users")
	public List<UserResponse> listUsers() {
		return adminService.getListUsers();
	}
	
	@GetMapping("/list-challenges")
	public List<UserResponse> listChallenges() {
		return adminService.getListUsers();
	}
	
	@PostMapping("/create-challenge")
	public ChallengeResponse createChallenge(@RequestBody ChallengeRequest challengeRequest) {
		return adminService.createChallenge(challengeRequest);
	}
	
	@DeleteMapping("/delete-challenge/{id}")
	public void deleteChallenge(@PathVariable int id) {
		adminService.deleteChallenge(id);
	}
	
	@PutMapping("/update-challenge/{id}")
	public ChallengeResponse updateChallenge(@PathVariable int id, @RequestBody ChallengeRequest challengeRequest) {
		return adminService.updateChallenge(id, challengeRequest);
	}
}
