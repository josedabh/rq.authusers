package com.rq.manager.authusers.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class HistoryChallenges.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryChallenges {

	/** The history challenges. */
	List<RegisterChallenge> historyChallenges;
}
