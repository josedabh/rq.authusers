package com.rq.manager.authusers.bean;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class ChallengeSummary.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeCard {
	
	/** The id. */
	@Schema(description = "Challenge ID", example = "123e4567-e89b-12d3-a456-426614174000")
	private UUID id;
	
	/** The title. */
	@Schema(description = "Challenge title", example = "Fitness Challenge")
	private String title;
	
	/** The description. */
	@Schema(description = "the description", example = "description")
	private String description;

}
