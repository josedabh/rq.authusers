package com.rq.manager.authusers.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rq.manager.authusers.bean.ChallengeRequest;
import com.rq.manager.authusers.bean.ChallengeResponse;
import com.rq.manager.authusers.bean.ChallengeSummary;
import com.rq.manager.authusers.constants.Constants;
import com.rq.manager.authusers.entity.Challenge;
import com.rq.manager.authusers.entity.StatesChallengeEnum;
import com.rq.manager.authusers.entity.User;
import com.rq.manager.authusers.entity.UserChallenge;
import com.rq.manager.authusers.exceptions.BusinessException;
import com.rq.manager.authusers.exceptions.ErrorConstants;
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
	 * Creates the challenge.
	 *
	 * @param challengeRequest the challenge request
	 * @return the challenge response
	 */
	public ChallengeResponse createChallenge(ChallengeRequest challengeRequest) {
		Challenge challenge = ChallengeMapper.mapRequestToEntity(challengeRequest);
		challenge.setState(StatesChallengeEnum.PENDING);
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
		Challenge challenge = challengeRepository.findById(id)
				.orElse(new Challenge());
		//Mira que el estado del reto está en pendiente
		if (Constants.PENDING.equals(challenge.getState().getState())) {
			challenge = ChallengeMapper.mapRequestToEntity(request);
			challengeRepository.save(challenge);
			return ChallengeMapper.mapEntityToResponse(challenge);
		} else {
			//Cambiar el throw
			throw new BusinessException("Challenge is not in pending state");
		}
		
	}

	/**
	 * Delete challenge by id.
	 *
	 * @param id the id challenge
	 */
	public void deleteChallenge(UUID id) {
		Challenge challenge = challengeRepository.findById(id)
				.orElse(new Challenge());
		if (Constants.PENDING.equals(challenge.getState().getState())
				|| Constants.CANCELLED.equals(challenge.getState().getState())) {
			challengeRepository.deleteById(id);
		} else {
			throw new BusinessException("Challenge is not in pending state");
		}
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
	public ChallengeResponse cancelChallenge(UUID id) {
		Challenge challenge = challengeRepository.findById(id).orElseThrow(null);
		if (Constants.IN_PROGRESS.equals(challenge.getState().getState())) {
			challenge.setState(StatesChallengeEnum.CANCELLED);
			challengeRepository.save(challenge);
		}
		return ChallengeMapper.mapEntityToResponse(challenge);
	}

	/**
	 * Join challenge.
	 *
	 * @param userId the user id
	 * @param challengeId the challenge id
	 */
	@Transactional
	public void joinChallenge(UUID userId, UUID challengeId) {
		Challenge challenge = challengeRepository.findById(challengeId)
				.orElseThrow(() -> new ResourceNotFoundException("Challenge not found with id: " + challengeId));
		
		if (Constants.IN_PROGRESS.equals(challenge.getState().getState())
				|| Constants.PENDING.equals(challenge.getState().getState())) {
			
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
			if (userChallengeRepository.existsByUserAndChallenge(user, challenge)) {
				throw new BusinessException(ErrorConstants.USER_ALREADY_JOINED_CHALLENGE);
			}
			UserChallenge userChallenge = new UserChallenge();
			userChallenge.setUser(user);
			userChallenge.setChallenge(challenge);
			userChallenge.setCompletedAt(new Date());
			userChallengeRepository.save(userChallenge);
		} else {
            throw new BusinessException(ErrorConstants.NOT_STATUS_CHALLENGE);
        }
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
