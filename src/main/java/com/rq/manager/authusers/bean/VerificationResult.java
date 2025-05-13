package com.rq.manager.authusers.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class VerificationResult.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationResult {
	
	/** The total questions. */
	private int totalQuestions;
	
	/** The correct answers. */
	private int correctAnswers;
	
	/** The percentage. */
	private double percentage;
	
	/** The passed. */
	private boolean passed;
}
