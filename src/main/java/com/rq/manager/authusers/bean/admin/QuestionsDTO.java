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
 * The Class QuestionsDTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NotBlank
public class QuestionsDTO {

	/** The question id. */
	@Schema(description = "the question id", example = "1212")
	private String questionId;
	
	/** The question. */
	@Schema(description = "the question ask the user", 
			example = "¿Que color menciona el autor como la tristeza que tiene un pinguino "
					+ "al no encontrar una gasolinera?")
	private String question;
	
	/** The answers. */
	@Schema(description = "the options answers that the user have to ask")
	private List<AnswerDTO> answers;
}
