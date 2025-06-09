package com.rq.manager.authusers.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rq.manager.authusers.bean.ChallengeRequest;
import com.rq.manager.authusers.bean.admin.ChallengeResponse;
import com.rq.manager.authusers.constants.Constants;
import com.rq.manager.authusers.entity.Challenge;
import com.rq.manager.authusers.entity.User;
import com.rq.manager.authusers.entity.UserChallenge;
import com.rq.manager.authusers.enumerations.CategoryEnum;
import com.rq.manager.authusers.enumerations.StatesChallengeEnum;
import com.rq.manager.authusers.exceptions.BusinessException;
import com.rq.manager.authusers.exceptions.CustomException;
import com.rq.manager.authusers.exceptions.ErrorConstants;
import com.rq.manager.authusers.exceptions.ResourceNotFoundException;
import com.rq.manager.authusers.mapper.ChallengeMapper;
import com.rq.manager.authusers.repository.ChallengeRepository;
import com.rq.manager.authusers.repository.QuizVerificationRepository;
import com.rq.manager.authusers.repository.UserChallengeRepository;
import com.rq.manager.authusers.repository.UserRepository;
import com.rq.manager.authusers.util.Util;

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
		Challenge challenge = challengeRepository.findById(id).orElse(new Challenge());
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
		if (Constants.PENDING.equals(challenge.getState().getDescription())) {
			challenge = mapRequestToChallenge(challenge, request);
			challengeRepository.save(challenge);
			return ChallengeMapper.mapEntityToResponse(challenge);
		} else {
			throw new BusinessException(ErrorConstants.CHALLENGE_DIFFERENT_STATE);
		}
		
	}
	
	/**
	 * Map request to challenge.
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
	            .map(Util::getLocalDateTime)
	            .ifPresent(challenge::setStartDate);
	    Optional.ofNullable(request.getEndDate())
	            .map(Util::getLocalDateTime)
	            .ifPresent(challenge::setEndDate);
	    Optional.ofNullable(request.getDifficulty())
	            .ifPresent(challenge::setDifficulty);
	    Optional.ofNullable(request.getPoints())
                .ifPresent(challenge::setPoints);
        if (request.getCategory() != null) {
            challenge.setCategory(
                    CategoryEnum.setDescription(request.getCategory()));
        }
        return challenge;
    }

	/**
	 * Delete challenge by id.
	 *
	 * @param id the id challenge
	 */
	public void deleteChallenge(UUID id) {
		Challenge challenge = challengeRepository.findById(id)
				.orElse(new Challenge());
		if (Constants.PENDING.equals(challenge.getState().getDescription())
				|| Constants.CANCELLED.equals(challenge.getState().getDescription())) {
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
	public ChallengeResponse cancelChallenge(UUID id) {
		Challenge challenge = challengeRepository.findById(id).orElse(new Challenge());
		//Comprueba que el reto está en progreso
		if (Constants.IN_PROGRESS.equals(challenge.getState().getDescription())) {
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
	public void joinChallenge(UUID challengeId) {
		Challenge challenge = challengeRepository.findById(challengeId)
				.orElseThrow(() -> new ResourceNotFoundException("Challenge not found with id: " + challengeId));
		//Comprueba que el reto está en progreso o pendiente
		if (Constants.IN_PROGRESS.equals(challenge.getState().getDescription())
				|| Constants.PENDING.equals(challenge.getState().getDescription())) {
			User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
					.orElseThrow(() -> new CustomException(ErrorConstants.NULL_USER));
			//Comprueba que el usuario no haya participado en el reto
			if (userChallengeRepository.existsByUserAndChallenge(user, challenge)) {
				throw new BusinessException(ErrorConstants.USER_ALREADY_JOINED_CHALLENGE);
			}
			UserChallenge userChallenge = new UserChallenge();
			userChallenge.setUser(user);
			userChallenge.setChallenge(challenge);
			userChallenge.setJoinedAt(new Date());
			userChallenge.setCompleted(false);
			userChallenge.setCompletedAt(new Date());
			userChallengeRepository.save(userChallenge);
		} else {
            throw new BusinessException(ErrorConstants.NOT_STATUS_CHALLENGE);
        }
	}
	
    /**
     * Verifica si un usuario puede ver el reto antes de la fecha de inicio. -
     * Si es un evento instantáneo, solo se puede ver a partir de `startDate`. -
     * Si se permite verlo una semana antes, se puede acceder desde `startDate -
     * 7 días`.
     *
     * @param currentDate
     *            the current date
     * @param isInstantEvent
     *            the is instant event
     * @param startDate
     *            the start date
     * @return true, if successful
     */
    public boolean canUserSeeChallenge(LocalDateTime currentDate,
            boolean isInstantEvent, LocalDateTime startDate) {
        if (isInstantEvent) {
            return !currentDate.isBefore(startDate);
        } else {
            return !currentDate.isBefore(startDate.minusDays(7));
        }
    }

    /**
     * Genera el ID completo (prefijo + cinco dígitos) y se lo asigna al reto.
     * Devuelve el ID completo, p.ej. "Q00003".
     *
     * @param challengeId
     *            the challenge id
     * @param typeCode
     *            the type code
     * @return the string
     */
    @Transactional
    public String assignVerificationType(UUID challengeId, String typeCode) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Challenge not found: " + challengeId));

        // Si ya tiene verificación, no se genera una nueva
        if (challenge.getVerificationType() != null &&
            challenge.getVerificationType().equals(typeCode) &&
            challenge.getVerificationId() != null) {
            return typeCode + challenge.getVerificationId();
        }

        // Buscar si ya hay un verificationId del mismo tipo para este challenge
        String existingId = challengeRepository
            .findVerificationIdByTypeAndChallengeId(typeCode, challengeId); // <-- Este método debe existir en tu repo

        if (existingId != null) {
            challenge.setVerificationType(typeCode);
            challenge.setVerificationId(existingId);
        } else {
            // Generar nuevo ID
            String numeric = nextNumericForType(typeCode);
            challenge.setVerificationType(typeCode);
            challenge.setVerificationId(numeric);
        }

        challengeRepository.save(challenge);
        return typeCode + challenge.getVerificationId();
    }

    /**
     * Calcula el siguiente ID numérico de cinco dígitos para un prefijo dado.
     * Ej.: si para "Q" ya existen 00001, 00002, devuelve "00003".
     *
     * @param prefix
     *            the prefix
     * @return the string
     */
    public String nextNumericForType(String prefix) {
        // Consulta el valor máximo de verificationId para este tipo
        String maxNumeric =
                challengeRepository.findMaxVerificationIdByType(prefix);
        int next = 1;
        if (maxNumeric != null) {
            try {
                next = Integer.parseInt(maxNumeric) + 1;
            } catch (NumberFormatException e) {
                // Si algo raro sucede, reiniciamos a 1
                next = 1;
            }
        }
        return String.format("%05d", next);
    }
    
    /**
     * Delete verification type.
     *
     * @param challengeId the challenge id
     * @param typeCode the type code
     */
    @Transactional
    public void deleteVerificationType(UUID challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Challenge not found: " + challengeId));
        
        // Eliminar entidades relacionadas si existen
        if (challenge.getVerificationType() != null && challenge.getVerificationId() != null) {
            String fullVerificationId = challenge.getVerificationType() + challenge.getVerificationId();
            
            // Eliminar QuizVerification y sus dependencias en cascada
            quizVerificationRepository.findById(fullVerificationId)
                .ifPresent(quizVerification -> {
                    quizVerificationRepository.delete(quizVerification);
                });
        }
        
        // Limpiar campos en Challenge
        challenge.setVerificationType(null);
        challenge.setVerificationId(null);
        challengeRepository.save(challenge);
    }
}
