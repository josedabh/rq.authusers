package com.rq.manager.authusers.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rq.manager.authusers.entity.Challenge;

/**
 * The Interface ChallengeRepository.
 */
public interface ChallengeRepository extends JpaRepository<Challenge, UUID> {

	@Query("SELECT c FROM Challenge c WHERE LOWER(c.title) "
			+ "LIKE LOWER(CONCAT('%', :title, '%')) "
			+ "AND LENGTH(:title) >= 2")
	List<Challenge> searchByTitle(@Param("title") String title);

}
