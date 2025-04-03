package com.rq.manager.authusers.web;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rq.manager.authusers.bean.ChallengeRequest;
import com.rq.manager.authusers.bean.ChallengeResponse;
import com.rq.manager.authusers.bean.ChallengeSummary;
import com.rq.manager.authusers.bean.UserResponse;
import com.rq.manager.authusers.service.ChallengeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

/**
 * The Class ChallengeController.
 */
@RestController
@RequestMapping("/api/admin/challenge")
@AllArgsConstructor
@Tag(name = "ChallengeController", 
description = "Controlador de los retos")
@ApiResponses(value = {
		@ApiResponse(responseCode = "400", description = "BAD REQUEST")
})
public class ChallengeController {
	
	/** The challenge service. */
	private ChallengeService challengeService;
	
	/**
	 * List challenges.
	 *
	 * @return the list
	 */
	@GetMapping("/list-challenges")
	public List<ChallengeResponse> listChallenges() {
		return challengeService.listChallenges();
	}
	
	/**
	 * Creates the challenge.
	 *
	 * @param challengeRequest the challenge request
	 * @return the challenge response
	 */
	@Operation(summary = "Crear nuevo reto", description = "Crea un nuevo reto")
    @ApiResponse(responseCode = "201", description = "Creación del reto exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)))
	@PostMapping("/create-challenge")
	public ChallengeResponse createChallenge(@RequestBody ChallengeRequest challengeRequest) {
		return challengeService.createChallenge(challengeRequest);
	}
	
	/**
	 * Delete challenge.
	 *
	 * @param id the id
	 */
	@DeleteMapping("/delete-challenge/{id}")
	public void deleteChallenge(@PathVariable int id) {
		challengeService.deleteChallenge(id);
	}
	
	/**
	 * Update challenge.
	 *
	 * @param id the id
	 * @param challengeRequest the challenge request
	 * @return the challenge response
	 */
	@PutMapping("/update-challenge/{id}")
	public ChallengeResponse updateChallenge(@PathVariable int id, @RequestBody ChallengeRequest challengeRequest) {
		return challengeService.updateChallenge(id, challengeRequest);
	}
	
	/**
	 * Search challenges.
	 *
	 * @param title the title
	 * @return the list
	 */
	@GetMapping("/search-challenge")
	public List<ChallengeSummary> searchChallenges(@RequestParam(defaultValue = "", required = true) String title) {
		return challengeService.searchChallenge(title);
	}

}
