package com.rq.manager.authusers.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rq.manager.authusers.enumerations.UserChallengeStateEnum;
import com.rq.manager.authusers.repository.entity.Challenge;
import com.rq.manager.authusers.repository.entity.User;
import com.rq.manager.authusers.repository.entity.UserChallenge;

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
    // Permite verificar si un usuario ya se ha unido a un
    // reto.
    Optional<UserChallenge> findByUserAndChallenge(User user, Challenge challenge);

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
    Optional<UserChallenge> findByUserIdAndChallengeId(Long userId, Long challengeId);

    /**
     * Finds all UserChallenge entries for a given user
     * filtered by state.
     *
     * @param user
     *            the user entity
     * @param state
     *            the desired state
     * @return list of UserChallenges with that state
     */
    List<UserChallenge> findByUserAndState(User user, UserChallengeStateEnum state);
}
