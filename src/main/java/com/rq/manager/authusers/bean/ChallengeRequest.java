package com.rq.manager.authusers.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ChallengeRequest {

    /** The title. */
    @NotBlank
    @Size(min = 10, max = 200)
    @Schema(description = "the title", example = "title")
    private String title;

    /** The description. */
    @Size(min = 10, max = 200)
    @Schema(description = "the description", example = "description")
    private String description;

    /**
     * The difficulty (BEGINNER, INTERMEDIATE, ADVANCED,
     * EXPERT).
     */
    @NotBlank
    @Schema(description = "the difficulty", example = "BEGINNER")
    private String difficulty;

    /** The category. */
    @Size(min = 1, max = 200)
    @Schema(description = "the category", example = "DEPORTE")
    private String category;

    /** The start date (ISO 8601). */
    @NotBlank
    @Schema(description = "the start date", example = "2023-10-01T00:00:00")
    private String startDate;

    /** The end date (ISO 8601). */
    @NotBlank
    @Schema(description = "the end date", example = "2023-10-31T23:59:59")
    private String endDate;

    /** The points. */
    @NotNull
    @Schema(description = "the points", example = "100")
    private Integer points;
}
