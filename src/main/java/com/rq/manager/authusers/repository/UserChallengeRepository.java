package com.rq.manager.authusers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rq.manager.authusers.entity.Challenge;
import com.rq.manager.authusers.entity.User;
import com.rq.manager.authusers.entity.UserChallenge;

/**
 * The Interface UserChallengeRepository.
 */
public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long> {

	/**
	 * Find by user and challenge.
	 *
	 * @param user the user
	 * @param challenge the challenge
	 * @return the optional
	 */
	// Permite verificar si un usuario ya se ha unido a un reto.
	Optional<UserChallenge> findByUserAndChallenge(User user, Challenge challenge);
}
