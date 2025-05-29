package com.rq.manager.authusers.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rq.manager.authusers.bean.admin.AnswerDTO;
import com.rq.manager.authusers.bean.admin.QuestionsDTO;
import com.rq.manager.authusers.bean.admin.QuizSubmitRequest;
import com.rq.manager.authusers.entity.Challenge;
import com.rq.manager.authusers.entity.QuizAnswer;
import com.rq.manager.authusers.entity.QuizQuestion;
import com.rq.manager.authusers.entity.QuizVerification;
import com.rq.manager.authusers.repository.ChallengeRepository;
import com.rq.manager.authusers.repository.QuizVerificationRepository;

import lombok.AllArgsConstructor;

/**
 * The Class VerificationService.
 */
@Service
@AllArgsConstructor
public class VerificationService {

    /** The challenge repo. */
    private final ChallengeRepository challengeRepo;
    
    /** The quiz verification repo. */
    private final QuizVerificationRepository quizVerificationRepo;

    /**
     * Crea un quiz de preguntas/respuestas asociándolo al reto.
     * Asume que el reto ya tiene su verificationType="Q" y verificationId="00003".
     *
     * @param request the request
     * @return the string
     */
    @Transactional
    public String createQuizVerification(QuizSubmitRequest request) {
        
        QuizVerification quizVerification = quizVerificationRepo.findById(request.getQuizId())
                .orElse(null);
        // 1) Recuperar reto y ID completo del quiz
        Challenge ch = quizVerification.getChallenge();

        String fullQuizId = ch.getVerificationType() + ch.getVerificationId(); // e.g. "Q00003"

        // 2) Crear entidad QuizVerification
        QuizVerification quiz = new QuizVerification();
        quiz.setId(fullQuizId);
        quiz.setChallenge(ch);

        // 3) Mapear preguntas y respuestas
        int qIndex = 1;
        for (QuestionsDTO qDto : request.getQuestions()) {
            String qId = fullQuizId + "-P" + String.format("%02d", qIndex++);
            QuizQuestion qEntity = new QuizQuestion(qId, qDto.getTitle(), quiz);

            int aIndex = 1;
            for (AnswerDTO aDto : qDto.getAnswers()) {
                String aId = qId + "-R" + String.format("%02d", aIndex++);
                QuizAnswer aEntity = new QuizAnswer(aId, aDto.getText(), aDto.isCorrect(), qEntity);
                qEntity.getAnswers().add(aEntity);
            }
            quiz.getQuestions().add(qEntity);
        }

        // 4) Persistir todo en cascada
        quizVerificationRepo.save(quiz);

        return fullQuizId;
    }
}
