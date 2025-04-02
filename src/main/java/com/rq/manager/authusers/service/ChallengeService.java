package com.rq.manager.authusers.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rq.manager.authusers.bean.ChallengeRequest;
import com.rq.manager.authusers.bean.ChallengeResponse;
import com.rq.manager.authusers.bean.ChallengeSummary;
import com.rq.manager.authusers.entity.Challenge;
import com.rq.manager.authusers.mapper.AdminMapper;
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
		// Averiguar como hacer lo de localDateTime
		Challenge challenge = AdminMapper.mapChallengeRToEntity(challengeRequest);
		challengeRepository.save(challenge);
		return AdminMapper.mapChallengeEntityToResponse(challenge);
	}

	/**
	 * List all challenges.
	 *
	 * @return the list challenges
	 */
	public List<ChallengeResponse> listChallenges() {
		return challengeRepository.findAll().stream()
				.map(challenge -> AdminMapper.mapChallengeEntityToResponse(challenge)).collect(Collectors.toList());
	}

	/**
	 * Update challenge.
	 *
	 * @param id      the id
	 * @param request the challenge request
	 * @return the challenge response
	 */
	public ChallengeResponse updateChallenge(int id, ChallengeRequest request) {
		Challenge challenge = challengeRepository.findById(id).orElseThrow(null);
		challenge = AdminMapper.mapChallengeRToEntity(request);
		challengeRepository.save(challenge);
		return AdminMapper.mapChallengeEntityToResponse(challenge);
	}

	/**
	 * Delete challenge.
	 *
	 * @param id the id challenge
	 */
	public void deleteChallenge(int id) {
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
				.map(ch -> AdminMapper.mapChallengeEToSummary(ch)).toList();
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

}
