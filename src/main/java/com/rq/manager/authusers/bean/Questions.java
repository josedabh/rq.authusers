package com.rq.manager.authusers.bean;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Questions {

	@Schema(description = "questionId")
	private String questionId;
	
	@Schema(description = "question")
	private String question;
	
	@Schema(description = "questionType")
	private List<String> answer;
}
