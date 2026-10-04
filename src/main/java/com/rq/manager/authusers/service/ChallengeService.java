package com.rq.manager.authusers.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rq.manager.authusers.bean.ChallengeHistoryResponse;
import com.rq.manager.authusers.bean.ChallengeRequest;
import com.rq.manager.authusers.bean.admin.ChallengeResponse;
import com.rq.manager.authusers.enumerations.CategoryEnum;
import com.rq.manager.authusers.enumerations.ChallengeVerificationType;
import com.rq.manager.authusers.enumerations.DifficultyEnum;
import com.rq.manager.authusers.enumerations.StatesChallengeEnum;
import com.rq.manager.authusers.enumerations.UserChallengeStateEnum;
import com.rq.manager.authusers.exceptions.BusinessException;
import com.rq.manager.authusers.exceptions.CustomException;
import com.rq.manager.authusers.exceptions.ErrorConstants;
import com.rq.manager.authusers.exceptions.ResourceNotFoundException;
import com.rq.manager.authusers.mapper.ChallengeMapper;
import com.rq.manager.authusers.mapper.UserChallengeMapper;
import com.rq.manager.authusers.repository.ChallengeRepository;
import com.rq.manager.authusers.repository.QuizVerificationRepository;
import com.rq.manager.authusers.repository.UserChallengeRepository;
import com.rq.manager.authusers.repository.UserRepository;
import com.rq.manager.authusers.repository.entity.Challenge;
import com.rq.manager.authusers.repository.entity.User;
import com.rq.manager.authusers.repository.entity.UserChallenge;
import com.rq.manager.authusers.util.DateUtil;

import lombok.AllArgsConstructor;

/**
 * The Class ChallengeService.
 */
@Service
@AllArgsConstructor
public class ChallengeService {

	/** The challenge repository. */
	private ChallengeRepository challengeRepository;

	/** The user challenge repository. */
	private UserChallengeRepository userChallengeRepository;

	/** The user repository. */
	private UserRepository userRepository;

	/** The quiz verification repository. */
	private QuizVerificationRepository quizVerificationRepository;

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
		return challengeRepository.findAll().stream()
				.map(ChallengeMapper::mapEntityToResponse)
				.collect(Collectors.toList());
	}

	/**
	 * Gets the challenge by id.
	 *
	 * @param id the id
	 * @return the challenge by id
	 */
	public ChallengeResponse getChallengeById(Long id) {
		Challenge challenge = challengeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.CHALLENGE_NOT_FOUND));
		return ChallengeMapper.mapEntityToResponse(challenge);
	}

	/**
	 * Update challenge.
	 *
	 * @param id      the id
	 * @param request the challenge request
	 * @return the challenge response
	 */
	public ChallengeResponse updateChallenge(Long id, ChallengeRequest request) {
		Challenge challenge = challengeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.CHALLENGE_NOT_FOUND));
		if (challenge.getState() == StatesChallengeEnum.PENDING) {
			challenge = mapRequestToChallenge(challenge, request);
			challengeRepository.save(challenge);
			return ChallengeMapper.mapEntityToResponse(challenge);
		} else {
			throw new BusinessException(ErrorConstants.CHALLENGE_DIFFERENT_STATE);
		}
	}

	/**
	 * Map request to challenge (partial update).
	 *
	 * @param challenge the challenge
	 * @param request the request
	 * @return the challenge
	 */
	private Challenge mapRequestToChallenge(Challenge challenge, ChallengeRequest request) {
	    Optional.ofNullable(request.getTitle())
	            .ifPresent(challenge::setTitle);
	    Optional.ofNullable(request.getDescription())
	            .ifPresent(challenge::setDescription);
	    Optional.ofNullable(request.getStartDate())
	            .map(DateUtil::parse)
	            .ifPresent(challenge::setStartDate);
	    Optional.ofNullable(request.getEndDate())
	            .map(DateUtil::parse)
	            .ifPresent(challenge::setEndDate);
	    Optional.ofNullable(request.getDifficulty())
	            .map(DifficultyEnum::fromDescription)
	            .ifPresent(challenge::setDifficulty);
	    Optional.ofNullable(request.getPoints())
                .ifPresent(challenge::setPoints);
        if (request.getCategory() != null) {
            challenge.setCategory(CategoryEnum.setDescription(request.getCategory()));
        }
        return challenge;
    }

	/**
	 * Delete challenge by id.
	 *
	 * @param id the id challenge
	 */
	public void deleteChallenge(Long id) {
		Challenge challenge = challengeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.CHALLENGE_NOT_FOUND));
		if (challenge.getState() == StatesChallengeEnum.PENDING
				|| challenge.getState() == StatesChallengeEnum.CANCELLED) {
			challengeRepository.deleteById(id);
		} else {
			throw new BusinessException(ErrorConstants.CHALLENGE_DIFFERENT_STATE);
		}
	}

	/**
	 * Cancel challenge.
	 *
	 * @param id the id
	 * @return the challenge response
	 */
	public ChallengeResponse cancelChallenge(Long id) {
		Challenge challenge = challengeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.CHALLENGE_NOT_FOUND));
		if (challenge.getState() == StatesChallengeEnum.IN_PROGRESS) {
			challenge.setState(StatesChallengeEnum.CANCELLED);
			challengeRepository.save(challenge);
			return ChallengeMapper.mapEntityToResponse(challenge);
		} else {
			throw new BusinessException(ErrorConstants.CHALLENGE_DIFFERENT_STATE);
		}
	}

	/**
	 * Join challenge.
	 *
	 * @param challengeId the challenge id
	 */
	@Transactional
	public void joinChallenge(Long challengeId) {
	    Challenge challenge = challengeRepository.findById(challengeId)
	        .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.CHALLENGE_NOT_FOUND));
	    if (challenge.getState() != StatesChallengeEnum.PENDING
	        && challenge.getState() != StatesChallengeEnum.IN_PROGRESS) {
	        throw new BusinessException(ErrorConstants.NOT_STATUS_CHALLENGE);
	    }
	    User user = userRepository.findByUsername(
	            SecurityContextHolder.getContext().getAuthentication().getName())
	        .orElseThrow(() -> new CustomException(ErrorConstants.NULL_USER));

	    Optional<UserChallenge> existingUc = userChallengeRepository.findByUserAndChallenge(user, challenge);

	    if (existingUc.isPresent()) {
	        UserChallenge userChallenge = existingUc.get();
	        if (canRejoinChallenge(userChallenge)) {
	            userChallenge.setJoinedAt(LocalDateTime.now());
	            userChallengeRepository.save(userChallenge);
	            return;
	        } else {
	            throw new BusinessException(ErrorConstants.USER_ALREADY_JOINED_CHALLENGE);
	        }
	    }
	    UserChallenge uc = new UserChallenge();
	    uc.setUser(user);
	    uc.setChallenge(challenge);
	    uc.setJoinedAt(LocalDateTime.now());
	    uc.setAttempts(0);
	    uc.setState(UserChallengeStateEnum.JOINED);
	    userChallengeRepository.save(uc);
	}

	/**
	 * Checks whether a user can re-join a challenge (no attempts and not finished).
	 */
	private boolean canRejoinChallenge(UserChallenge userChallenge) {
	    return userChallenge.getAttempts() == 0
	        && userChallenge.getState() != UserChallengeStateEnum.COMPLETED
	        && userChallenge.getState() != UserChallengeStateEnum.FAILED;
	}

	/**
	 * List challenges visible to the authenticated user.
	 *
	 * @return the list challenge
	 */
	public List<ChallengeResponse> listChallengesForUser() {
	    LocalDateTime currentDate = LocalDateTime.now();
	    return challengeRepository.findAll().stream()
	        .filter(challenge -> isChallengeVisible(challenge, currentDate))
	        .map(ChallengeMapper::mapEntityToResponse)
	        .collect(Collectors.toList());
	}

	private boolean isChallengeVisible(Challenge challenge, LocalDateTime currentDate) {
	    StatesChallengeEnum state = challenge.getState();
	    LocalDateTime startDate = challenge.getStartDate();
	    LocalDateTime endDate   = challenge.getEndDate();

	    if (state == null || startDate == null || endDate == null) {
	        return false;
	    }

	    switch (state) {
	        case PENDING:
	            return isWithinTwoWeeksBeforeStart(currentDate, startDate);
	        case IN_PROGRESS:
	            return isBetweenInclusive(currentDate, startDate, endDate);
	        case FINISHED:
	        case CANCELLED:
	            return isWithinTwoWeeksAfterEnd(currentDate, endDate);
	        default:
	            return false;
	    }
	}

	private boolean isBetweenInclusive(LocalDateTime current, LocalDateTime start, LocalDateTime end) {
	    return !current.isBefore(start) && !current.isAfter(end);
	}

	private boolean isWithinTwoWeeksBeforeStart(LocalDateTime current, LocalDateTime startDate) {
	    LocalDateTime twoWeeksBefore = startDate.minusWeeks(2);
	    return !current.isBefore(twoWeeksBefore) && current.isBefore(startDate);
	}

	private boolean isWithinTwoWeeksAfterEnd(LocalDateTime current, LocalDateTime endDate) {
	    LocalDateTime twoWeeksAfter = endDate.plusWeeks(2);
	    return current.isAfter(endDate) && !current.isAfter(twoWeeksAfter);
	}

	/**
	 * Start challenge (PENDING → IN_PROGRESS).
	 *
	 * @param challengeId the challenge id
	 * @return the challenge response
	 */
	@Transactional
	public ChallengeResponse startChallenge(Long challengeId) {
	    Challenge challenge = challengeRepository.findById(challengeId)
	        .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.CHALLENGE_NOT_FOUND));

	    if (!isChallengeStartable(challenge)) {
	        throw new BusinessException(ErrorConstants.CHALLENGE_CANNOT_BE_STARTED);
	    }

	    challenge.setState(StatesChallengeEnum.IN_PROGRESS);
	    challenge.setStartDate(LocalDateTime.now());
	    challengeRepository.save(challenge);
	    return ChallengeMapper.mapEntityToResponse(challenge);
	}

	private boolean isChallengeStartable(Challenge challenge) {
	    return challenge.getState() == StatesChallengeEnum.PENDING
	            && challenge.getVerificationType() != null;
	}

    /**
     * Assigns a verification type to a challenge and generates its verification ID.
     *
     * @param challengeId the challenge id
     * @param typeCode    the type code (Q, I, L)
     * @return the full verification ID (e.g. "Q00003")
     */
    @Transactional
    public String assignVerificationType(Long challengeId, String typeCode) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.CHALLENGE_NOT_FOUND));
        ChallengeVerificationType verificationType = ChallengeVerificationType.fromCode(typeCode);

        if (challenge.getVerificationType() != null &&
            challenge.getVerificationType() == verificationType &&
            challenge.getVerificationId() != null) {
            return typeCode + challenge.getVerificationId();
        }

        String existingId = challengeRepository
            .findVerificationIdByTypeAndChallengeId(verificationType, challengeId);

        if (existingId != null) {
            challenge.setVerificationType(verificationType);
            challenge.setVerificationId(existingId);
        } else {
            String numeric = nextNumericForType(verificationType);
            challenge.setVerificationType(verificationType);
            challenge.setVerificationId(numeric);
        }

        challengeRepository.save(challenge);
        return typeCode + challenge.getVerificationId();
    }

    /**
     * Returns the next five-digit numeric string for a given verification type prefix.
     *
     * @param prefix the single-character type prefix (Q, I, L)
     * @return next numeric string (e.g. "00003")
     */
    public String nextNumericForType(String prefix) {
        return nextNumericForType(ChallengeVerificationType.fromCode(prefix));
    }

    private String nextNumericForType(ChallengeVerificationType type) {
        String maxNumeric = challengeRepository.findMaxVerificationIdByType(type);
        int next = 1;
        if (maxNumeric != null) {
            try {
                next = Integer.parseInt(maxNumeric) + 1;
            } catch (NumberFormatException e) {
                next = 1;
            }
        }
        return String.format("%05d", next);
    }

    /**
     * Removes the verification type from a challenge and deletes the associated quiz.
     *
     * @param challengeId the challenge id
     */
    @Transactional
    public void deleteVerificationType(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.CHALLENGE_NOT_FOUND));

        if (challenge.getVerificationType() != null && challenge.getVerificationId() != null) {
            String fullVerificationId = challenge.getVerificationType().getCode() + challenge.getVerificationId();
            quizVerificationRepository.findById(fullVerificationId)
                .ifPresent(quizVerificationRepository::delete);
        }

        challenge.setVerificationType(null);
        challenge.setVerificationId(null);
        challengeRepository.save(challenge);
    }

    /**
     * Returns the completed challenge history for the authenticated user.
     *
     * @return list of completed challenge history responses
     */
    public List<ChallengeHistoryResponse> listCompletedChallengesForUser() {
        User user = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName())
            .orElseThrow(() -> new CustomException(ErrorConstants.NULL_USER));
        return userChallengeRepository
            .findByUserAndState(user, UserChallengeStateEnum.COMPLETED)
            .stream()
            .map(UserChallengeMapper::mapUserChallengeToResponse)
            .collect(Collectors.toList());
    }
}
