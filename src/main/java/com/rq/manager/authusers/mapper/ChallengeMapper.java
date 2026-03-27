package com.rq.manager.authusers.mapper;

import com.rq.manager.authusers.bean.ChallengeRequest;
import com.rq.manager.authusers.bean.admin.ChallengeResponse;
import com.rq.manager.authusers.entity.Challenge;
import com.rq.manager.authusers.enumerations.CategoryEnum;
import com.rq.manager.authusers.enumerations.ChallengeVerificationType;
import com.rq.manager.authusers.util.Util;

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
		challenge.setCategory(CategoryEnum.setDescription(request.getCategory()));
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
				.category(entity.getCategory().getDescription())
				.state(entity.getState() != null ? entity.getState().getDescription() : "NOTSTATE")
				.startDate(Util.getDate(entity.getStartDate()))
				.endDate(Util.getDate(entity.getEndDate()))
				.verificationNumber(entity.getVerificationId())
				.verificationType(entity.getVerificationType() != null
						? entity.getVerificationType().getCode()
						: null)
				.points(entity.getPoints()).build();
	}
	
}
