package com.rq.manager.authusers.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rq.manager.authusers.entity.Challenge;

/**
 * The Interface ChallengeRepository.
 */
public interface ChallengeRepository extends JpaRepository<Challenge, UUID> {

	/**
	 * Devuelve la parte numérica (cinco dígitos) más alta ya registrada
	 * para un determinado tipo de verificación.
	 * Por ejemplo, si existen Q00001, Q00005, devuelve "00005".
	 *
	 * @param type the type
	 * @return the string
	 */
    @Query("SELECT MAX(c.verificationId) FROM Challenge c WHERE c.verificationType = :type")
    String findMaxVerificationIdByType(@Param("type") String type);
    
    /**
     * Find verification id by type and challenge id.
     *
     * @param typeCode the type code
     * @param challengeId the challenge id
     * @return the string
     */
    @Query("SELECT c.verificationId FROM Challenge c WHERE c.verificationType = :typeCode AND c.id = :challengeId")
    String findVerificationIdByTypeAndChallengeId(@Param("typeCode") String typeCode, @Param("challengeId") UUID challengeId);

    /**
     * Find by verification type and verification id.
     *
     * @param type the type
     * @param verificationId the verification id
     * @return the optional
     */
    Optional<Challenge> findByVerificationTypeAndVerificationId(String type, String verificationId);

}
