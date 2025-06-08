package com.rq.manager.authusers.bean.admin;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class ChallengeResponse.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeResponse {

	/** The id. */
	@Schema(description = "the id", example = "dfew32fv-23f2-4f23-a2f3-123456789abc")
	private UUID id;
	
	/** The title. */
	@Schema(description = "the title", example = "title")
	private String title;
	
	/** The description. */
	@Schema(description = "the description", example = "description")
	private String description;
	
	/** The difficulty. */
	@Schema(description = "the difficulty", example = "easy")
	private String difficulty;
	
    /** The category. */
    @Schema(description = "the category", example = "deportes")
    private String category;
	
	/** The state. */
	@Schema(description = "the state", example = "active")
	private String state;
	
	/** The start date. */
	@Schema(description = "the start date", example = "2023-10-01T00:00:00")
	private String startDate;
	
	/** The end date. */
	@Schema(description = "the end date", example = "2023-10-31T23:59:59")
	private String endDate;
	
	/** The points. */
	@Schema(description = "the points", example = "2")
	private int points;
	
	@Schema(description = "the verification type", example = "Q | L | I")
	private String verificationType;
	
	@Schema(description = "the verification number", example = "12345678")
	private String verificationNumber;
}