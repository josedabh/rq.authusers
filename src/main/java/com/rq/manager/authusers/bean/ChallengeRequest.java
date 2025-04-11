package com.rq.manager.authusers.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class ChallengeRequest.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NotBlank
public class ChallengeRequest {

	/** The title. */
	@Size(min = 10, max = 200)
	@Schema(description = "the title", example = "title")
	private String title;

	/** The description. */
	@Size(min = 10, max = 200)
	@Schema(description = "the description", example = "description")
	private String description;

	/** The difficulty. */
	@Size(min = 10, max = 200)
	@Schema(description = "the difficulty", example = "easy")
	private String difficulty;

//	/** The state. */
//	@Schema(description = "the state", example = "active")
//	private String state;

	/** The start date. */
	@Schema(description = "the start date", example = "2023-10-01T00:00:00")
	private String startDate;

	/** The end date. */
	@Schema(description = "the end date", example = "2023-10-31T23:59:59")
	private String endDate;

	/** The points. */
	@Size(min = 1, max = 5)
	@Schema(description = "the points", example = "2")
	private int points;

}
