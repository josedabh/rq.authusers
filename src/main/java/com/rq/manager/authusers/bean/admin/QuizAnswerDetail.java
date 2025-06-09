package com.rq.manager.authusers.bean.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class QuizAnswerDetail.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Quiz answer details")
public class QuizAnswerDetail {
    
    /** The answer id. */
    @Schema(description = "The answer ID", example = "Q00001-P01-R01")
    private String answerId;
    
    /** The text. */
    @Schema(description = "The answer text", example = "Paris")
    private String text;
}
