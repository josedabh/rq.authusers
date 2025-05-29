package com.rq.manager.authusers.repository;

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
     */
    @Query("SELECT MAX(c.verificationId) FROM Challenge c WHERE c.verificationType = :type")
    String findMaxVerificationIdByType(@Param("type") String type);
}
