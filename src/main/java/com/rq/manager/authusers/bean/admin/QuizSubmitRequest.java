package com.rq.manager.authusers.bean.admin;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class QuizSubmitRequest.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NotBlank
public class QuizSubmitRequest {
    
    /** The quiz id. */
	@Schema(description = "the quiz id", example = "1212")
    private String quizId;
    
    /** The questions. */
	@Schema(description = "the questions")
    private List<QuestionsDTO> questions;
    
}
