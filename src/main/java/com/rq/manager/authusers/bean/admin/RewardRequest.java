package com.rq.manager.authusers.bean.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class RewardRequest.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@NotBlank
public class RewardRequest {
	
	/** The name. */
	@Size(min = 1, max = 80)
	@Schema(description = "Reward name", example = "Reward 1")
	private String name;
	
	/** The description. */
	@Size(min = 1, max = 200)
	@Schema(description = "Reward description", example = "Reward 1 description")
	private String description;
	
	/** The points. */
	@NotBlank
	@Schema(description = "Reward points", example = "100")
	private Integer points;
	
	/** The image. */
	@Schema(description = "Reward image", example = "reward1.png")
	private String image;
	
	/** The active. */
	@NotBlank
	@Schema(description = "Reward active status", example = "true")
	private boolean visible;
	
	/** The stock. */
	@NotBlank
	@Schema(description = "Reward stock", example = "2")
	private Integer stock;
}
