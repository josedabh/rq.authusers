package com.rq.manager.authusers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rq.manager.authusers.entity.QuizAnswer;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, String>{

}
