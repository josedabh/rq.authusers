package com.rq.manager.authusers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rq.manager.authusers.entity.QuizQuestion;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, String>{

}
