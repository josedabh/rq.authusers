package com.rq.manager.authusers.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rq.manager.authusers.bean.ChallengeRequest;
import com.rq.manager.authusers.bean.ChallengeResponse;
import com.rq.manager.authusers.bean.ChallengeSummary;
import com.rq.manager.authusers.entity.Challenge;
import com.rq.manager.authusers.entity.User;
import com.rq.manager.authusers.entity.UserChallenge;
import com.rq.manager.authusers.exceptions.BusinessException;
import com.rq.manager.authusers.exceptions.ResourceNotFoundException;
import com.rq.manager.authusers.mapper.ChallengeMapper;
import com.rq.manager.authusers.repository.ChallengeRepository;
import com.rq.manager.authusers.repository.UserChallengeRepository;
import com.rq.manager.authusers.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 * The Class ChallengeService.
 */
@Service
@AllArgsConstructor
public class ChallengeService {

	/** The challenge repository. */
	private ChallengeRepository challengeRepository;
	
	/** The user repository. */
	private UserRepository userRepository;
	
	/** The user challenge repository. */
	private UserChallengeRepository userChallengeRepository;

	/**
	 * The user challenge repository.
	 *
	 * @param challengeRequest the challenge request
	 * @return the challenge response
	 */
//	private UserChallengeRepository userChallengeRepository;

	/**
	 * Creates the challenge.
	 *
	 * @param challengeRequest the challenge request
	 * @return the challenge response
	 */
	public ChallengeResponse createChallenge(ChallengeRequest challengeRequest) {
		Challenge challenge = ChallengeMapper.mapRequestToEntity(challengeRequest);
		challenge.setState("Pending");
		challengeRepository.save(challenge);
		return ChallengeMapper.mapEntityToResponse(challenge);
	}

	/**
	 * List all challenges.
	 *
	 * @return the list challenges
	 */
	public List<ChallengeResponse> listChallenges() {
		return challengeRepository.findAll().stream().map(challenge -> ChallengeMapper.mapEntityToResponse(challenge))
				.collect(Collectors.toList());
	}

	/**
	 * Gets the challenge by id.
	 *
	 * @param id the id
	 * @return the challenge by id
	 */
	public ChallengeResponse getChallengeById(UUID id) {
		Challenge challenge = challengeRepository.findById(id).orElseThrow(null);
		return ChallengeMapper.mapEntityToResponse(challenge);
	}

	/**
	 * Update challenge.
	 *
	 * @param id      the id
	 * @param request the challenge request
	 * @return the challenge response
	 */
	public ChallengeResponse updateChallenge(UUID id, ChallengeRequest request) {
		Challenge challenge = challengeRepository.findById(id).orElseThrow(null);
		challenge = ChallengeMapper.mapRequestToEntity(request);
		challengeRepository.save(challenge);
		return ChallengeMapper.mapEntityToResponse(challenge);
	}

	/**
	 * Delete challenge by id.
	 *
	 * @param id the id challenge
	 */
	public void deleteChallenge(UUID id) {
		challengeRepository.deleteById(id);
	}

	/**
	 * Search challenge.
	 *
	 * @param title the title challenge
	 * @return the list challenge searched
	 */
	public List<ChallengeSummary> searchChallenge(String title) {
		List<ChallengeSummary> searchedChallenges = challengeRepository.searchByTitle(title).stream()
				.map(ch -> ChallengeMapper.mapEntityToSummary(ch)).toList();
		return searchedChallenges;
	}

	/**
	 * Cancel challenge.
	 *
	 * @param id the id
	 */
	public void cancelChallenge(UUID id) {
		Challenge challenge = challengeRepository.findById(id).orElseThrow(null);
		challenge.setState("Cancelled");
		challengeRepository.save(challenge);
	}

	@Transactional
	public String joinChallenge(UUID userId, UUID challengeId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		Challenge challenge = challengeRepository.findById(challengeId)
				.orElseThrow(() -> new ResourceNotFoundException("Challenge not found with id: " + challengeId));

		if (userChallengeRepository.existsByUserAndChallenge(user, challenge)) {
			throw new BusinessException("User is already registered in this challenge");
		}

		if ("Cancelled".equals(challenge.getState())) {
			throw new BusinessException("Cannot join a cancelled challenge");
		}

//		UserChallenge userChallenge = UserChallenge.builder().user(user).challenge(challenge)
//				.joinDate(LocalDateTime.now()).earnedPoints(0).build();
		UserChallenge userChallenge = new UserChallenge();
		userChallengeRepository.save(userChallenge);
		return "Successfully joined the challenge";
	}

	
	/**
	 * Verifica si un usuario puede ver el reto antes de la fecha de inicio. - Si es
	 * un evento instantáneo, solo se puede ver a partir de `startDate`. - Si se
	 * permite verlo una semana antes, se puede acceder desde `startDate - 7 días`.
	 *
	 * @param currentDate    the current date
	 * @param isInstantEvent the is instant event
	 * @param startDate
	 * @return true, if successful
	 */
	public boolean canUserSeeChallenge(LocalDateTime currentDate, boolean isInstantEvent, LocalDateTime startDate) {
		if (isInstantEvent) {
			return !currentDate.isBefore(startDate);
		} else {
			return !currentDate.isBefore(startDate.minusDays(7));
		}
	}

}
