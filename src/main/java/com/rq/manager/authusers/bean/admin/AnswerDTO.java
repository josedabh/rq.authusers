package com.rq.manager.authusers.bean.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class AnswerDTO.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NotBlank
public class AnswerDTO {

	/** The answer id. */
	@Schema(description = "the answer ID",  example = "1")
	private String answerId;

	/** The result. */
	@Schema(description = "the answer result", example = "rojo")
	private String result;

	/** The is correct. */
	@Schema(description = "is the answer correct", example = "false")
	private Boolean isCorrect;

}
