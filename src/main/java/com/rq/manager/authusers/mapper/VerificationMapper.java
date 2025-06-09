package com.rq.manager.authusers.mapper;

import java.util.HashMap;
import java.util.Map;

import com.rq.manager.authusers.bean.admin.QuizSubmitResponse;
import com.rq.manager.authusers.entity.QuizAnswer;
import com.rq.manager.authusers.entity.QuizQuestion;
import com.rq.manager.authusers.entity.QuizVerification;

/**
 * The Class VerificationMapper.
 */
public class VerificationMapper {
    
    /**
     * Instantiates a new verification mapper.
     */
    private VerificationMapper() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Map quiz verification to submit response.
     *
     * @param quizVer
     *            the quiz ver
     * @return the quiz submit response
     */
    public static QuizSubmitResponse mapQuizVerificationToSubmitResponse(QuizVerification quizVer) {
        // Crear el objeto QuizSubmitResponse
        QuizSubmitResponse quizSubmitResponse = new QuizSubmitResponse();
        quizSubmitResponse.setQuizId(quizVer.getId());
        quizSubmitResponse.setTotalQuestions(quizVer.getQuestions().size());
        
        // Inicializar correctAnswers y score
        quizSubmitResponse.setCorrectAnswers(0); // Inicialmente 0, se puede calcular más adelante
        quizSubmitResponse.setScore(0.0); // Inicialmente 0.0, se puede calcular más adelante
        // Crear un mapa para los resultados de las preguntas
        Map<String, Boolean> questionResults = new HashMap<>();
        for (QuizQuestion question : quizVer.getQuestions()) {
            for (QuizAnswer answer : question.getAnswers()) {
                // Aquí se puede agregar lógica para determinar si la respuesta es correcta
                questionResults.put(question.getId(), answer.getCorrect());
            }
        }
        quizSubmitResponse.setQuestionResults(questionResults);
        return quizSubmitResponse;
    }
}
