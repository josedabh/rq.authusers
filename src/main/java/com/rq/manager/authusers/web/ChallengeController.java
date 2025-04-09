package com.rq.manager.authusers.web;

import java.util.List;
import java.util.UUID;

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
import com.rq.manager.authusers.constants.Constants;
import com.rq.manager.authusers.exceptions.ErrorResponse;
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
		@ApiResponse(responseCode = "400", 
			description = Constants.BAD_REQUEST, 
			content = @Content(mediaType = Constants.APPLICATION_JSON,
			schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "401", 
			description = Constants.UNAUTHORIZED, 
			content = @Content(mediaType = Constants.APPLICATION_JSON, 
			schema = @Schema(implementation = ErrorResponse.class))) 
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
	 * Gets the challenge by id.
	 *
	 * @param id the id
	 * @return the challenge by id
	 */
	@Operation(summary = "Encontrar reto por id", description = "Busca un reto por id")
    @ApiResponse(responseCode = "200", description = "El reto ha sido encontrado",
            content = @Content(mediaType = Constants.APPLICATION_JSON,
            schema = @Schema(implementation = ChallengeResponse.class)))
	@GetMapping("/find-challenge/{id}")
	public ChallengeResponse getChallengeById(@PathVariable UUID id) {
		return challengeService.getChallengeById(id);
	}
	
	
	/**
	 * Creates the challenge.
	 *
	 * @param challengeRequest the challenge request
	 * @return the challenge response
	 */
	@Operation(summary = "Crear nuevo reto", description = "Crea un nuevo reto")
    @ApiResponse(responseCode = "200", description = "Creación del reto exitosamente",
            content = @Content(mediaType = Constants.APPLICATION_JSON,
            schema = @Schema(implementation = ChallengeResponse.class)))
	@PostMapping("/create-challenge")
	public ChallengeResponse createChallenge(@RequestBody ChallengeRequest 
			challengeRequest) {
		return challengeService.createChallenge(challengeRequest);
	}
	
	/**
	 * Delete challenge.
	 *
	 * @param id the id
	 */
	@Operation(summary = "Eliminar reto por id", description = "Elimina un reto por id")
	@ApiResponse(responseCode = "200", description = "El reto ha sido eliminado", 
		content = @Content(mediaType = Constants.APPLICATION_JSON, 
		schema = @Schema(implementation = Void.class)))
	@DeleteMapping("/delete-challenge/{id}")
	public void deleteChallenge(@PathVariable UUID id) {
		challengeService.deleteChallenge(id);
	}
	
	/**
	 * Update challenge.
	 *
	 * @param id the id
	 * @param challengeRequest the challenge request
	 * @return the challenge response
	 */
	@Operation(summary = "Actualizar reto por id", description = "Actualiza un reto por id")
	@ApiResponse(responseCode = "200", description = "El reto ha sido actualizado", 
	content = @Content(mediaType = Constants.APPLICATION_JSON, 
	schema = @Schema(implementation = ChallengeResponse.class)))
	@PutMapping("/update-challenge/{id}")
	public ChallengeResponse updateChallenge(@PathVariable UUID id,
			@RequestBody ChallengeRequest challengeRequest) {
		return challengeService.updateChallenge(id, challengeRequest);
	}
	
	/**
	 * Search challenges.
	 *
	 * @param title the title
	 * @return the list
	 */
	@Operation(summary = "Buscar retos por título", description = "Busca retos por título")
	@ApiResponse(responseCode = "200", description = "Los retos han sido encontrados",
		content = @Content(mediaType = Constants.APPLICATION_JSON, 
		schema = @Schema(implementation = ChallengeSummary.class)))
	@GetMapping("/search-challenge")
	public List<ChallengeSummary> searchChallenges(@RequestParam(defaultValue = "",
		required = true) String title) {
		return challengeService.searchChallenge(title);
	}
	
	@PostMapping("/{userId}/join/{challengeId}")
	@Operation(summary = "Unirse a un reto", 
		description = "Permite a un usuario unirse a un reto específico")
	@ApiResponse(responseCode = "200", 
		description = "Usuario unido al reto exitosamente")
	public void joinChallenge(@PathVariable UUID userId, 
			@PathVariable UUID challengeId) {
		challengeService.joinChallenge(userId, challengeId);
	}

}
