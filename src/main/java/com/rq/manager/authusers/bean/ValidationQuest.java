package com.rq.manager.authusers.bean;

import java.util.List;

import com.rq.manager.authusers.bean.admin.Questions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class ValidationQuest.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationQuest {

	/** The type. */
	@Schema(description = "the type", example = "quest")
	private String type;
	
	/** The questions. */
	@Schema(description = "the questions")
	private List<Questions> questions;
}
