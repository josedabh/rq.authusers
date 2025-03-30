package com.rq.manager.authusers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rq.manager.authusers.entity.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {

}
