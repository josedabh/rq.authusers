package com.rq.manager.authusers.mapper;

import com.rq.manager.authusers.bean.ChallengeRequest;
import com.rq.manager.authusers.bean.admin.ChallengeResponse;
import com.rq.manager.authusers.enumerations.CategoryEnum;
import com.rq.manager.authusers.enumerations.DifficultyEnum;
import com.rq.manager.authusers.repository.entity.Challenge;
import com.rq.manager.authusers.util.DateUtil;

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
		challenge.setStartDate(DateUtil.parse(request.getStartDate()));
		challenge.setEndDate(DateUtil.parse(request.getEndDate()));
		challenge.setDifficulty(DifficultyEnum.fromDescription(request.getDifficulty()));
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
		return ChallengeResponse.builder()
				.id(entity.getId() != null ? entity.getId().toString() : null)
				.title(entity.getTitle())
				.description(entity.getDescription())
				.difficulty(entity.getDifficulty() != null ? entity.getDifficulty().getDescription() : null)
				.category(entity.getCategory() != null ? entity.getCategory().getDescription() : null)
				.state(entity.getState() != null ? entity.getState().getDescription() : "NOTSTATE")
				.startDate(DateUtil.format(entity.getStartDate()))
				.endDate(DateUtil.format(entity.getEndDate()))
				.verificationNumber(entity.getVerificationId())
				.verificationType(entity.getVerificationType() != null
						? entity.getVerificationType().getCode()
						: null)
				.points(entity.getPoints())
				.build();
	}

}
