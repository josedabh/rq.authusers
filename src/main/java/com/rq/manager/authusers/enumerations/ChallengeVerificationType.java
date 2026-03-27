package com.rq.manager.authusers.enumerations;

import com.rq.manager.authusers.exceptions.BusinessException;
import com.rq.manager.authusers.exceptions.ErrorConstants;

public enum ChallengeVerificationType {
	QUIZ("Q", "QUIZ"),
	IMAGE("I", "IMAGE"),
	LOCATION("L", "LOCATION");
	
	private String code;
	
	private String type;
	
	private ChallengeVerificationType(String code, String type) {
		this.code = code;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public String getCode() {
		return code;
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
			if (verificationType.getCode().equals(code)) {
				return verificationType;
			}
		}
		throw new BusinessException(ErrorConstants.INVALID_VERIFICATION_TYPE);
	}
}
