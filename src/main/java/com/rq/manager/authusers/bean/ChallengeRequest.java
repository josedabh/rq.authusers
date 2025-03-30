package com.rq.manager.authusers.bean;

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
	private String title;
	
	@Size(min = 10, max=200)
	private String description;
	
	@Size(min = 10, max=200)
	private String difficulty;
	
	@Size(min = 1, max = 3)
	private int days;
	
	@Size(min = 1, max = 5)
	private int points;

}
