package com.rq.manager.authusers.bean;

import java.util.UUID;

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
	
	private UUID id;
	
	private String title;
	
	private String description;
	
	private String difficulty;
	
	private int points;

}