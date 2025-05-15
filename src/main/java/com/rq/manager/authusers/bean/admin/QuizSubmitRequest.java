package com.rq.manager.authusers.bean.admin;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class QuizSubmitRequest.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizSubmitRequest {
    
    /** The quiz id. */
	@Schema(description = "the quiz id", example = "1212")
    private String quizId;
    
    /** The title. */
	@Schema(description = "the title", example = "Los colores en la antartida")
    private String title;
    
    /** The questions. */
	@Schema(description = "the questions")
    private List<QuestionsDTO> questions;
    
}
