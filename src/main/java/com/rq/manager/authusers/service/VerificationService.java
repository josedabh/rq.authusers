package com.rq.manager.authusers.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rq.manager.authusers.bean.VerificationResult;
import com.rq.manager.authusers.bean.admin.AnswerDTO;
import com.rq.manager.authusers.bean.admin.QuizSubmitRequest;
import com.rq.manager.authusers.entity.Challenge;
import com.rq.manager.authusers.entity.QuizAnswer;
import com.rq.manager.authusers.entity.QuizVerification;
import com.rq.manager.authusers.enumerations.StatesChallengeEnum;
import com.rq.manager.authusers.exceptions.BusinessException;
import com.rq.manager.authusers.exceptions.ErrorConstants;
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

    /** The quiz verification repository. */
    private final QuizVerificationRepository quizVerificationRepository;

//    /** The quiz question repository. */
//    private final QuizQuestionRepository quizQuestionRepository;
//
//    /** The quiz answer repository. */
//    private final QuizAnswerRepository quizAnswerRepository;

    /**
 * Creates the quiz verfication.
 *
 * @param quizSubmitRequest the quiz submit request
 */
	public void createQuizVerfication(QuizSubmitRequest quizSubmitRequest) {
//		QuizVerification quiz = mapQuizRequestToVerification(quizSubmitRequest);
//		List<QuizQuestion> questions = quizSubmitRequest.getQuestions().stream().map(q -> {
//			QuizQuestion question = new QuizQuestion();
//			question.setAnswers(q.getAnswers().stream().map(answer -> {
//				return mapAnswerRequestToEntity(answer);
//			}).collect(Collectors.toList()));
//			return question;
//		}).collect(Collectors.toList());
//		quiz.setQuestions(questions);
//		quizVerificationRepository.save(quiz);
    }
	
	/**
	 * Map answer request to entity.
	 *
	 * @param a the a
	 * @return the quiz answer
	 */
	private QuizAnswer mapAnswerRequestToEntity(AnswerDTO a) {
		QuizAnswer answer = new QuizAnswer();
		answer.setCorrect(a.isCorrect());
		return answer;
	}
	
	/**
	 * Map quiz request to verification.
	 *
	 * @param quizSubmitRequest the quiz submit request
	 * @return the quiz verification
	 */
	private QuizVerification mapQuizRequestToVerification(QuizSubmitRequest quizSubmitRequest) {
		QuizVerification quiz = new QuizVerification();
		quiz.setId(quizSubmitRequest.getQuizId());
		return quiz;
	}

    /**
     * Obtiene el quiz completo (preguntas y respuestas) para un reto dado.
     *
     * @param challengeId
     *            the challenge id
     * @return the quiz for challenge
     */
    @Transactional(readOnly = true)
    public QuizVerification getQuizForChallenge(UUID challengeId) {
        Challenge ch = challengeRepo.findById(challengeId)
                .orElseThrow(() -> new BusinessException(
                        ErrorConstants.CHALLENGE_NOT_FOUND));

        String qId = ch.getVerificationId();
        return quizVerificationRepository.findById(qId)
                .orElseThrow(() -> new BusinessException(
                        ErrorConstants.QUIZ_NOT_FOUND));
    }

    /**
     * Recibe las respuestas del usuario (map) y valida frente al quiz, devuelve
     * el porcentaje de acierto y, en caso de éxito total, marca el reto como
     * FINALIZADO.
     *
     * @param challengeId
     *            UUID del reto
     * @param answersMap
     *            Map<String questionId, Long answerIdSeleccionada>
     * @return the verification result
     */
    @Transactional
    public VerificationResult submitQuiz(UUID challengeId,
            Map<String, Long> answersMap) {
        // 1. Cargar reto y quiz
        Challenge ch = challengeRepo.findById(challengeId)
                .orElse(new Challenge());
        QuizVerification quiz = getQuizForChallenge(challengeId);
        // 2. Validar que reto esté en estado PENDIENTE o INICIADO
        if (ch.getState() != StatesChallengeEnum.PENDING &&
                ch.getState() != StatesChallengeEnum.IN_PROGRESS) {
            throw new BusinessException(
                    ErrorConstants.CHALLENGE_DIFFERENT_STATE);
        }
        // 3. Recorrer las preguntas y comparar
        int total = quiz.getQuestions().size();
//        int correctCount = (int) quiz.getQuestions().stream()
//        	    .filter(q -> {
//        	        Long selected = answersMap.get(q.getQuestionId());
//        	        if (selected == null) return false;
//
//        	        return q.getAnswers().stream()
//        	            .filter(QuizAnswer::isCorrect)
//        	            .anyMatch(a -> a.getAnswerId() != null && a.getAnswerId().toString().equals(selected.toString()));
//        	    })
//        	    .count();
        int correctCount = 0;
        // 4. Calcular porcentaje
        double scorePercent = total > 0
                ? (correctCount * 100.0) / total
                : 0.0;

        // 5. Si logra el mínimo requerido (por ejemplo 70%), finalizar el reto
        boolean passed = scorePercent >= 70.0;
        if (passed) {
            ch.setState(StatesChallengeEnum.FINISHED);
            challengeRepo.save(ch);
        }
        // 6. Devolver un objeto con resultado
        return new VerificationResult(total, correctCount, scorePercent,
                passed);
	}
}
