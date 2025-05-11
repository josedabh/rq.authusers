package com.rq.manager.authusers.service;

import org.springframework.stereotype.Service;

import com.rq.manager.authusers.entity.QuizVerification;
import com.rq.manager.authusers.repository.QuizAnswerRepository;
import com.rq.manager.authusers.repository.QuizQuestionRepository;
import com.rq.manager.authusers.repository.QuizVerificationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VerificationService {
    
    private final QuizVerificationRepository quizVerificationRepository;
    
    private final QuizQuestionRepository quizQuestionRepository;

    private final QuizAnswerRepository quizAnswerRepository;
    
    public void createQuizVerfication(String id) {
        QuizVerification quizVerification = new QuizVerification();
        String idprueba = "I000001";
        quizVerification.setId(idprueba);
        quizVerificationRepository.save(quizVerification);
    }
}
