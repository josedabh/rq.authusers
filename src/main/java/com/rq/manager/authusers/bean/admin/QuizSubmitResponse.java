package com.rq.manager.authusers.bean.admin;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class QuizSubmitResponse.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizSubmitResponse {
    
    /** The quiz id. */
	@Schema(description = "the quiz id", example = "1212")
    private String quizId;
    
    /** The total questions. */
	@Schema(description = "the total questions", example = "21")
    private int totalQuestions;
    
    /** The correct answers. */
	@Schema(description = "the answers that user have correct", example = "0")
    private int correctAnswers;
    
    /** The score. */
	@Schema(description = "the number that user have progress", example = "90.3")
    private double score;
    
    /** The question results. */
	@Schema(description = "the result of the question")
    private Map<String, Boolean> questionResults; // questionId -> isCorrect
}

