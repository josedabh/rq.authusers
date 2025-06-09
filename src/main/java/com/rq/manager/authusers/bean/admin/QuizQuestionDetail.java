package com.rq.manager.authusers.bean.admin;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class QuizQuestionDetail.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Quiz question details")
public class QuizQuestionDetail {
    
    /** The question id. */
    @Schema(description = "The question ID", example = "Q00001-P01")
    private String questionId;
    
    /** The title. */
    @Schema(description = "The question text", example = "What is the capital of France?")
    private String title;
    
    /** The answers. */
    @Schema(description = "List of possible answers")
    private List<QuizAnswerDetail> answers;
}
