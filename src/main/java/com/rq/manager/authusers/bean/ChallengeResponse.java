package com.rq.manager.authusers.bean;

import java.time.LocalDateTime;

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
public class ChallengeResponse {
	
	private int id;
	
	private String title;
	
	private String description;
	
	private String difficulty;
	
	private LocalDateTime duration;
	
	private int points;

}