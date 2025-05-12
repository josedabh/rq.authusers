package com.rq.manager.authusers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rq.manager.authusers.entity.QuizVerification;

public interface QuizVerificationRepository extends JpaRepository<QuizVerification, String> {

}
