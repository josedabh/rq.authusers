package com.rq.manager.authusers.web;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rq.manager.authusers.bean.admin.QuizSubmitRequest;
import com.rq.manager.authusers.constants.ApiConstants;
import com.rq.manager.authusers.constants.Constants;
import com.rq.manager.authusers.entity.QuizVerification;
import com.rq.manager.authusers.service.VerificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * The Class VerificationController.
 */
@RestController
@RequestMapping("/api/v1/verification/challenge")
@AllArgsConstructor
@Tag(name = "Verification Challenge", description = "Controller for verification challenge")
@ApiResponses(value = { @ApiResponse(responseCode = "400", description = ApiConstants.BAD_REQUEST),
@ApiResponse(responseCode = "401", description = Constants.UNAUTHORIZED),
@ApiResponse(responseCode = "404", description = ApiConstants.NOT_FOUND),
   @ApiResponse(responseCode = "500", description = ApiConstants.INTERNAL_SERVER_ERROR)
})
public class VerificationController {

	/** The verification service. */
	private VerificationService verificationService;
	
	/**
	 * Creates the quiz verification.
	 *
	 * @param quizSubmitRequest the quiz submit request
	 */
	@Operation(summary = "Crea un nuevo quiz de preguntas", description = "Crea un nuevo quiz de preguntas")
	@ApiResponse(responseCode = "200", description = "Quiz verification created successfully")
	@PostMapping("/quiz")
	public void createQuizVerification(@Valid @RequestBody
		@Parameter(description = "Quiz submit request", required = true)
			QuizSubmitRequest quizSubmitRequest) {
		verificationService.createQuizVerification(quizSubmitRequest);
	}
	
	/**
	 * Gets the quiz for challenge.
	 *
	 * @param quizId the quiz id
	 * @return the quiz for challenge
	 */
	@Operation(summary = "Get quiz for challenge", description = "Get quiz for challenge")
	@ApiResponse(responseCode = "200", description = "Quiz verification retrieved successfully")
	@GetMapping("/quiz/{quizId}")
	public QuizVerification getQuizForChallenge(@PathVariable 
			@Parameter(description = "Quiz ID", required = true)
			UUID quizId) {
        return verificationService.getQuizForChallenge(quizId);
	}
	
//	@Operation(summary = "Submit quiz for challenge", description = "Submit quiz for challenge")
//	@ApiResponse(responseCode = "200", description = "Quiz verification submitted successfully")
//	@PostMapping("/submit-quiz")
//	public VerificationResult submitQuizForChallenge(
//			@Valid @RequestBody @Parameter(description = "Quiz submit request", required = true) QuizSubmitRequest quizSubmitRequest) {
//		return verificationService.submitQuiz(quizSubmitRequest);
//	}
	
}
