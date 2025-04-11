package com.rq.manager.authusers.mapper;

import com.rq.manager.authusers.bean.RegisterChallenge;
import com.rq.manager.authusers.entity.UserChallenge;

public class UserChallengeMapper {
	
	public UserChallengeMapper() {
		// Default constructor
	}

	public static RegisterChallenge mapEntityToResponse(UserChallenge userChallenge) {

//		 RegisterChallenge.builder().id(userChallenge.getId()).userId(userChallenge.getUser().getId())
//				.challengeId(userChallenge.getChallenge().getId()).state(userChallenge.getState().getState())
//				.startDate(userChallenge.getStartDate()).endDate(userChallenge.getEndDate()).build()

		return RegisterChallenge.builder().build();
	}

}
