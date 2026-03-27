package com.rq.manager.authusers.enumerations;

import com.rq.manager.authusers.exceptions.BusinessException;
import com.rq.manager.authusers.exceptions.ErrorConstants;

public enum ChallengeVerificationType {
	QUIZ("Q", "QUIZ"),
	IMAGE("I", "IMAGE"),
	LOCATION("L", "LOCATION");
	
	private String verificationId;
	
	private String type;
	
	private ChallengeVerificationType(String verificationId, String type) {
		this.verificationId = verificationId;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public String getVerificationId() {
		return verificationId;
	}

	public static ChallengeVerificationType fromType(String type) {
		for (ChallengeVerificationType verificationType : values()) {
			if (verificationType.getType().equals(type)) {
				return verificationType;
			}
		}
		throw new BusinessException(ErrorConstants.INVALID_VERIFICATION_TYPE);
	}

	public static ChallengeVerificationType fromCode(String code) {
		for (ChallengeVerificationType verificationType : values()) {
			if (verificationType.getVerificationId().equals(code)) {
				return verificationType;
			}
		}
		throw new BusinessException(ErrorConstants.INVALID_VERIFICATION_TYPE);
	}
}
