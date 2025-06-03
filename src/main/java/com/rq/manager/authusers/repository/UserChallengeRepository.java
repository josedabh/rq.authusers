package com.rq.manager.authusers.repository;

import java.util.Optional;
import java.util.UUID;

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
     * @param user
     *            the user
     * @param challenge
     *            the challenge
     * @return the optional
     */
    // Permite verificar si un usuario ya se ha unido a un reto.
    Optional<UserChallenge> findByUserAndChallenge(User user,
            Challenge challenge);

    /**
     * Exists by user and challenge.
     *
     * @param user
     *            the user
     * @param challenge
     *            the challenge
     * @return true, if successful
     */
    boolean existsByUserAndChallenge(User user, Challenge challenge);
    
    /**
     * Find by user id and challenge id.
     *
     * @param userId
     *            the user id
     * @param challengeId
     *            the challenge id
     * @return the optional
     */
    Optional<UserChallenge> findByUserIdAndChallengeId(UUID userId,
            UUID challengeId);

}
