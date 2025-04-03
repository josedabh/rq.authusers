package com.rq.manager.authusers.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NotBlank
public class ChallengeRequest {
	
	@Size(min = 10, max=200)
	@Schema(description = "the title", example = "title")
	private String title;
	
	@Size(min = 10, max=200)
	@Schema(description = "the description", example = "description")
	private String description;
	
	@Size(min = 10, max=200)
	@Schema(description = "the difficulty", example = "easy")
	private String difficulty;
	
	@Size(min = 1, max = 3)
	@Schema(description = "the duration", example = "3")
	private int days;
	
	@Size(min = 1, max = 5)
	@Schema(description = "the points", example = "2")
	private int points;

}
