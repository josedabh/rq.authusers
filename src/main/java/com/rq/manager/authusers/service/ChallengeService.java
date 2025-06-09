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

import com.rq.manager.authusers.bean.ChallengeHistoryResponse;
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
import com.rq.manager.authusers.mapper.UserChallengeMapper;
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
	    // 1) Cargar reto y validar estado
	    Challenge challenge = challengeRepository.findById(challengeId)
	        .orElseThrow(() -> new ResourceNotFoundException(
	            "Challenge not found with id: " + challengeId));
	    if (challenge.getState() != StatesChallengeEnum.PENDING
	        && challenge.getState() != StatesChallengeEnum.IN_PROGRESS) {
	        throw new BusinessException(ErrorConstants.NOT_STATUS_CHALLENGE);
	    }
	    // 2) Cargar usuario autenticado
	    User user = userRepository.findByUsername(
	            SecurityContextHolder.getContext().getAuthentication().getName())
	        .orElseThrow(() -> new CustomException(ErrorConstants.NULL_USER));
	    // 3) Verificar duplicados
	    if (userChallengeRepository.existsByUserAndChallenge(user, challenge)) {
	        throw new BusinessException(ErrorConstants.USER_ALREADY_JOINED_CHALLENGE);
	    }
	    // 4) Crear UserChallenge
	    UserChallenge uc = new UserChallenge();
	    uc.setUser(user);
	    uc.setChallenge(challenge);
	    uc.setJoinedAt(new Date());
	    uc.setAttempts(0);
	    uc.setCompleted(false);
	    // 5) Persistir
	    userChallengeRepository.save(uc);
	}

	
	/**
	 * List challenges for user.
	 *
	 * @return the list challenge
	 */
	public List<ChallengeResponse> listChallengesForUser() {
	    LocalDateTime currentDate = LocalDateTime.now();
	    List<Challenge> allChallenges = challengeRepository.findAll();
	    
	    return allChallenges.stream()
	        .filter(challenge -> isChallengeVisible(challenge, currentDate))
	        .map(ChallengeMapper::mapEntityToResponse)
	        .collect(Collectors.toList());
	}

	/**
	 * Comprueba si el reto debe mostrarse al usuario en la fecha dada.
	 *
	 * @param challenge the challenge
	 * @param currentDate the current date
	 * @return true, if is challenge visible
	 */
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
	            // Visible desde startDate hasta endDate, ambos inclusive
	            return isBetweenInclusive(currentDate, startDate, endDate);

	        case FINISHED:
	        case CANCELLED:
	            return isWithinTwoWeeksAfterEnd(currentDate, endDate);

	        default:
	            return false;
	    }
	}

	/**
	 * Devuelve true si current ∈ [start, end].
	 *
	 * @param current the current
	 * @param start the start
	 * @param end the end
	 * @return true, if is between inclusive
	 */
	private boolean isBetweenInclusive(LocalDateTime current, LocalDateTime start, LocalDateTime end) {
	    return !current.isBefore(start) && !current.isAfter(end);
	}

	/**
	 * Checks if is within two weeks before start.
	 *
	 * @param current the current
	 * @param startDate the start date
	 * @return true, if is within two weeks before start
	 */
	private boolean isWithinTwoWeeksBeforeStart(LocalDateTime current, LocalDateTime startDate) {
	    LocalDateTime twoWeeksBefore = startDate.minusWeeks(2);
	    // current ∈ [twoWeeksBefore, startDate)
	    return !current.isBefore(twoWeeksBefore) && current.isBefore(startDate);
	}

	/**
	 * Checks if is within two weeks after end.
	 *
	 * @param current the current
	 * @param endDate the end date
	 * @return true, if is within two weeks after end
	 */
	private boolean isWithinTwoWeeksAfterEnd(LocalDateTime current, LocalDateTime endDate) {
	    LocalDateTime twoWeeksAfter = endDate.plusWeeks(2);
	    // current ∈ (endDate, twoWeeksAfter]
	    return current.isAfter(endDate) && !current.isAfter(twoWeeksAfter);
	}
	
	/**
	 * Start challenge.
	 *
	 * @param challengeId the challenge id
	 * @return the challenge response
	 */
	@Transactional
	public ChallengeResponse startChallenge(UUID challengeId) {
	    Challenge challenge = challengeRepository.findById(challengeId)
	        .orElseThrow(() -> new ResourceNotFoundException(
	            "Challenge not found with id: " + challengeId));

	    // Usamos el helper en lugar de repetir el chequeo manual
	    if (!isChallengeStartable(challenge)) {
	        throw new BusinessException(ErrorConstants.CHALLENGE_CANNOT_BE_STARTED);
	    }

	    // Actualizar estado y fecha
	    challenge.setState(StatesChallengeEnum.IN_PROGRESS);
	    challenge.setStartDate(LocalDateTime.now());

	    challengeRepository.save(challenge);
	    return ChallengeMapper.mapEntityToResponse(challenge);
	}

	/**
	 * Checks if is challenge startable.
	 *
	 * @param challenge the challenge
	 * @return true, if is challenge startable
	 */
	private boolean isChallengeStartable(Challenge challenge) {
	    return challenge.getState() == StatesChallengeEnum.PENDING;
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
    
    /**
     * List completed challenges history for the authenticated user.
     * 
     * @return List of ChallengeHistoryResponse beans with snapshot data.
     */
    public List<ChallengeHistoryResponse> listCompletedChallengesForUser() {
        // Get current authenticated username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Find user entity
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        // Query UserChallenge entities where completed=true for user
        List<UserChallenge> completedChallenges = userChallengeRepository.findByUserAndCompletedTrue(user);

        // Map using the mapper method
        return completedChallenges.stream()
                .map(UserChallengeMapper::mapUserChallengeToResponse) // <- usa tu mapper aquí
                .collect(Collectors.toList());
    }
}
