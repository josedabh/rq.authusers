package com.rq.manager.authusers.bean.admin;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class QuizDetailResponse.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Quiz details including questions and answers")
public class QuizDetailResponse {

    /** The quiz id. */
    @Schema(description = "The quiz ID", example = "Q00001")
    private String quizId;
    
    /** The questions. */
    @Schema(description = "List of questions in the quiz")
    private List<QuizQuestionDetail> questions;
}
