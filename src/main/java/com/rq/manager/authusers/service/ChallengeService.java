package com.rq.manager.authusers.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rq.manager.authusers.bean.ChallengeRequest;
import com.rq.manager.authusers.bean.ChallengeResponse;
import com.rq.manager.authusers.bean.ChallengeSummary;
import com.rq.manager.authusers.entity.Challenge;
import com.rq.manager.authusers.mapper.ChallengeMapper;
import com.rq.manager.authusers.repository.ChallengeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChallengeService {

	/** The challenge repository. */
	private ChallengeRepository challengeRepository;

	/** The user challenge repository. */
//	private UserChallengeRepository userChallengeRepository;

	/**
	 * Creates the challenge.
	 *
	 * @param challengeRequest the challenge request
	 * @return the challenge response
	 */
	public ChallengeResponse createChallenge(ChallengeRequest challengeRequest) {
		Challenge challenge = ChallengeMapper.mapRequestToEntity(challengeRequest);
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
				.map(challenge -> ChallengeMapper.mapEntityToResponse(challenge)).collect(Collectors.toList());
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
		List<ChallengeSummary> searchedChallenges = challengeRepository.findByTitle(title).stream()
				.map(ch -> ChallengeMapper.mapEntityToSummary(ch)).toList();
		return searchedChallenges;
	}

	/**
	 * Join challenge. modificar
	 * 
	 * @param userId      the user id
	 * @param challengeId the challenge id
	 * @return the string
	 */
//	@Transactional
//	public String joinChallenge(UUID userId, int challengeId) {
//		// Obtener el usuario
////		Optional<User> userOpt = userRepository.findById(userId);
//		if (userOpt.isEmpty()) {
//			return "Usuario no encontrado";
//		}
//
//		// Obtener el reto
//		Optional<Challenge> challengeOpt = challengeRepository.findById(challengeId);
//		if (challengeOpt.isEmpty()) {
//			return "Reto no encontrado";
//		}
//
//		User user = userOpt.get();
//		Challenge challenge = challengeOpt.get();
//
//		// Verificar si el usuario ya está unido al reto
//		Optional<UserChallenge> ucOpt = userChallengeRepository.findByUserAndChallenge(user, challenge);
//		if (ucOpt.isPresent()) {
//			return "El usuario ya está unido a este reto";
//		}
//
//		// Crear el registro de unión
//		UserChallenge userChallenge = new UserChallenge();
//		userChallenge.setUser(user);
//		userChallenge.setChallenge(challenge);
//		// completedAt se deja nulo hasta que el reto se complete
//		userChallenge.setEarnedPoints(0);
//
//		userChallengeRepository.save(userChallenge);
//
//		return "Inscripción al reto exitosa";
//	}
	
	/**
     * Verifica si un usuario puede ver el reto antes de la fecha de inicio.
     * - Si es un evento instantáneo, solo se puede ver a partir de `startDate`.
     * - Si se permite verlo una semana antes, se puede acceder desde `startDate - 7 días`.
     *
     * @param currentDate the current date
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
