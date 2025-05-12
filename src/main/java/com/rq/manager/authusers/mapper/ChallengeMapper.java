package com.rq.manager.authusers.mapper;

import com.rq.manager.authusers.bean.ChallengeCard;
import com.rq.manager.authusers.bean.ChallengeRequest;
import com.rq.manager.authusers.bean.admin.ChallengeResponse;
import com.rq.manager.authusers.entity.Challenge;
import com.rq.manager.authusers.util.Util;

// TODO: Auto-generated Javadoc
/**
 * The Class ChallengeMapper.
 */
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
				.difficulty(entity.getDifficulty())
				.state(entity.getState() != null ? entity.getState().getDescription() : "NOTSTATE")
				.startDate(Util.getDate(entity.getStartDate()))
				.endDate(Util.getDate(entity.getEndDate()))
				.points(entity.getPoints()).build();
	}
	
	/**
	 * Map entity to card.
	 *
	 * @param ch the ch
	 * @return the challenge card
	 */
	public static ChallengeCard mapEntityToCard(Challenge ch) {
		return ChallengeCard.builder()
				.title(ch.getTitle()).id(ch.getId()).description(ch.getDescription()).build();
	}
	
	
}
