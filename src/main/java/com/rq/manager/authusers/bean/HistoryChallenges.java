package com.rq.manager.authusers.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class HistoryChallenges.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryChallenges {


	/** The name challenge. */
	@Schema(description = "Nombre del reto", example = "Reto 1")
	private String nameChallenge;
	
	/** The description challenge. */
	@Schema(description = "Descripción del reto", example = "Descripción del reto 1")
	private String descriptionChallenge;
	
	/** The name user. */
	@Schema(description = "Nombre del usuario", example = "Usuario 1")
	private String nameUser;
	
	/** The points challenge. */
	@Schema(description = "Puntos del reto", example = "10")
	private int pointsChallenge;
	
	/** The start date. */
	@Schema(description = "Fecha de inicio del reto", example = "2023-10-01")
	private String startDate;
	
	/** The end date. */
	@Schema(description = "Fecha de fin del reto", example = "2023-10-31")
	private String endDate;
	
	/** The completion date. */
	@Schema(description = "Fecha de compleatado el reto", example = "2023-10-15")
	private String completionDate;
}
