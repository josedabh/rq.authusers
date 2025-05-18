package com.rq.manager.authusers.bean.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RewardResponse {
	
	/** The id. */
	@Schema(description = "Reward ID", example = "1")
	private long id;
	
	/** The name. */
	@Schema(description = "Reward name", example = "Reward 1")
	private String name;
	
	/** The description. */
	@Schema(description = "Reward description", example = "Reward 1 description")
	private String description;
	
	/** The points. */
	@Schema(description = "Reward points", example = "100")
	private int points;
	
	/** The image. */
	@Schema(description = "Reward image", example = "reward1.png")
	private String image;
	
	/** The active. */
	@Schema(description = "Reward active status", example = "true")
	private boolean visible;
	
	/** The stock. */
	@Schema(description = "Reward stock", example = "2")
	private int stock;

}