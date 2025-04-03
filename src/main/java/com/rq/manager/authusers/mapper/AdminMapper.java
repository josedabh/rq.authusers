package com.rq.manager.authusers.mapper;

import com.rq.manager.authusers.bean.ChallengeRequest;
import com.rq.manager.authusers.bean.ChallengeResponse;
import com.rq.manager.authusers.bean.ChallengeSummary;
import com.rq.manager.authusers.entity.Challenge;

/**
 * The Class AdminMapper.
 */
public class AdminMapper {
	
	/**
	 * Instantiates a new admin mapper.
	 */
	public AdminMapper() {
		
	}
	
	/**
	 * Map challenge request to entity.
	 *
	 * @param request the request
	 * @return the challenge
	 */
	public static Challenge mapChallengeRToEntity(ChallengeRequest request) {
		Challenge challenge = new Challenge();
		challenge.setTitle(request.getTitle());
		challenge.setDescription(request.getDescription());
		challenge.setDifficulty(request.getDescription());
//		challenge.setDuration(LocalDateTime.of(2000,01,01,10,30));
		challenge.setDifficulty(request.getDifficulty());
		challenge.setPoints(request.getPoints());
		return challenge;
	}

	/**
	 * Map challenge entity to response.
	 *
	 * @param entity the entity
	 * @return the challenge response
	 */
	public static ChallengeResponse mapChallengeEntityToResponse(Challenge entity) {
		return ChallengeResponse.builder().id(entity.getId())
				.title(entity.getTitle()).description(entity.getDescription())
				.difficulty(entity.getDifficulty())//.duration(entity.getDuration())
				.points(entity.getPoints()).build();
	}

	public static ChallengeSummary mapChallengeEToSummary(Challenge ch) {
		return ChallengeSummary.builder()
				.title(ch.getTitle()).id(ch.getId()).build();
	}

}
