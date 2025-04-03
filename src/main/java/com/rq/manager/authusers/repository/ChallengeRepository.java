package com.rq.manager.authusers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rq.manager.authusers.entity.Challenge;

/**
 * The Interface ChallengeRepository.
 */
public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {

	List<Challenge> findByTitle(String title);
}
