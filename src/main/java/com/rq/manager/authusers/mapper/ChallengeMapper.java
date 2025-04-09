package com.rq.manager.authusers.mapper;

import com.rq.manager.authusers.bean.ChallengeRequest;
import com.rq.manager.authusers.bean.ChallengeResponse;
import com.rq.manager.authusers.bean.ChallengeSummary;
import com.rq.manager.authusers.entity.Challenge;
import com.rq.manager.authusers.util.Util;

public class ChallengeMapper {
	
	/**
	 * Instantiates a new challenge mapper.
	 */
	public ChallengeMapper() {
		// Default constructor
	}

	/**
	 * Map challenge request to entity.
	 *
	 * @param request the request
	 * @return the challenge
	 */
	public static Challenge mapRequestToEntity(ChallengeRequest request) {
		Challenge challenge = new Challenge();
		challenge.setTitle(request.getTitle());
		challenge.setDescription(request.getDescription());
		challenge.setStartDate(Util.getLocalDateTime(request.getStartDate()));
		challenge.setEndDate(Util.getLocalDateTime(request.getEndDate()));
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
	public static ChallengeResponse mapEntityToResponse(Challenge entity) {
		return ChallengeResponse.builder().id(entity.getId())
				.title(entity.getTitle()).description(entity.getDescription())
				.difficulty(entity.getDifficulty()).state(entity.getState().getState())
				.startDate(Util.getDate(entity.getStartDate()))
				.endDate(Util.getDate(entity.getEndDate()))
				.points(entity.getPoints()).build();
	}

	/**
	 * Map entity to summary.
	 *
	 * @param ch the ch
	 * @return the challenge summary
	 */
	public static ChallengeSummary mapEntityToSummary(Challenge ch) {
		return ChallengeSummary.builder()
				.title(ch.getTitle()).id(ch.getId()).build();
	}
}
