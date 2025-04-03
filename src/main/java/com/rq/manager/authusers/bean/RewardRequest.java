package com.rq.manager.authusers.bean;

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
	private String name;
	
	/** The description. */
	@Size(min = 1, max = 200)
	private String description;
	
	/** The points. */
	private int points;
	
	/** The image. */
	private String image;
	
	/** The active. */
	private boolean active;
	
	/** The stock. */
	private int stock;

}
