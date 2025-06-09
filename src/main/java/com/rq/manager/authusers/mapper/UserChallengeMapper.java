package com.rq.manager.authusers.mapper;

import java.time.ZoneId;

import com.rq.manager.authusers.bean.ChallengeHistoryResponse;
import com.rq.manager.authusers.entity.UserChallenge;

public class UserChallengeMapper {
	
	public UserChallengeMapper() {
		// Default constructor
	}

	/**
	 * Map user challenge to response.
	 *
	 * @param uc the uc
	 * @return the challenge history response
	 */
	public static ChallengeHistoryResponse mapUserChallengeToResponse(UserChallenge uc) {
        if (uc == null) {
            return null;
        }
        return ChallengeHistoryResponse.builder()
                .userName(uc.getUser().getName())
                .userLastname(uc.getUser().getLastname())
                .userUsername(uc.getUser().getUsername())
                .challengeTitle(uc.getChallenge() != null ? uc.getChallenge().getTitle() : "")
                .completedAt(uc.getCompletedAt() != null
                    ? uc.getCompletedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                    : null)
                .earnedPoints(uc.getEarnedPoints() != null ? uc.getEarnedPoints() : 0)
                .attempts(uc.getAttempts())
                .build();
	}

}
