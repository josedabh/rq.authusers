package com.rq.manager.authusers.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rq.manager.authusers.bean.admin.AnswerDTO;
import com.rq.manager.authusers.bean.admin.QuestionsDTO;
import com.rq.manager.authusers.bean.admin.QuizSubmitRequest;
import com.rq.manager.authusers.bean.admin.QuizSubmitResponse;
import com.rq.manager.authusers.entity.Challenge;
import com.rq.manager.authusers.entity.QuizAnswer;
import com.rq.manager.authusers.entity.QuizQuestion;
import com.rq.manager.authusers.entity.QuizVerification;
import com.rq.manager.authusers.entity.UserChallenge;
import com.rq.manager.authusers.exceptions.BusinessException;
import com.rq.manager.authusers.exceptions.ErrorConstants;
import com.rq.manager.authusers.mapper.VerificationMapper;
import com.rq.manager.authusers.repository.ChallengeRepository;
import com.rq.manager.authusers.repository.QuizAnswerRepository;
import com.rq.manager.authusers.repository.QuizQuestionRepository;
import com.rq.manager.authusers.repository.QuizVerificationRepository;
import com.rq.manager.authusers.repository.UserChallengeRepository;

import lombok.AllArgsConstructor;

/**
 * The Class VerificationService.
 */
@Service
@AllArgsConstructor
public class VerificationService {

    /** The challenge repo. */
    private ChallengeRepository challengeRepo;

    /** The quiz verification repo. */
    private QuizVerificationRepository quizVerificationRepo;

    /** The quiz question repo. */
    private QuizQuestionRepository quizQuestionRepo;

    /** The quiz answer repo. */
    private QuizAnswerRepository quizAnswerRepo;
    
    /** The user challenge repository. */
    private UserChallengeRepository userChallengeRepository;

    /**
     * Creates the quiz verification.
     *
     * @param request
     *            the request
     * @return the string
     */
    @Transactional
    public String createQuizVerification(QuizSubmitRequest request) {
        QuizVerification quizVerification = quizVerificationRepo.findById(request.getQuizId())
                .orElseThrow(() -> new BusinessException(ErrorConstants.QUIZ_NOT_FOUND));

        Challenge challenge = quizVerification.getChallenge();
        String fullQuizId = challenge.getVerificationType() + challenge.getVerificationId();

        QuizVerification quiz = new QuizVerification();
        quiz.setId(fullQuizId);
        quiz.setChallenge(challenge);

        mapQuestionsAndAnswers(request, quiz, fullQuizId);

        quizVerificationRepo.save(quiz);
        return fullQuizId;
    }

    /**
     * Map questions and answers.
     *
     * @param request
     *            the request
     * @param quiz
     *            the quiz
     * @param fullQuizId
     *            the full quiz id
     */
    private void mapQuestionsAndAnswers(QuizSubmitRequest request, QuizVerification quiz, String fullQuizId) {
        int questionIndex = 1;
        for (QuestionsDTO questionDto : request.getQuestions()) {
            String questionId = fullQuizId + "-P" + String.format("%02d", questionIndex++);
            QuizQuestion questionEntity = new QuizQuestion();
            questionEntity.setId(questionId);
            questionEntity.setTitle(questionDto.getQuestion());
            questionEntity.setQuiz(quiz);
            mapAnswers(questionDto, questionEntity, questionId);
            quiz.getQuestions().add(questionEntity);
        }
    }
    
    /**
     * Map answers.
     *
     * @param questionDto
     *            the question dto
     * @param questionEntity
     *            the question entity
     * @param questionId
     *            the question id
     */
    private void mapAnswers(QuestionsDTO questionDto, QuizQuestion questionEntity, String questionId) {
        int answerIndex = 1;
        for (AnswerDTO answerDto : questionDto.getAnswers()) {
            String answerId = questionId + "-R" + String.format("%02d", answerIndex++);
            QuizAnswer answerEntity = new QuizAnswer();
            answerEntity.setId(answerId);
            answerEntity.setText(answerDto.getResult());
            answerEntity.setCorrect(answerDto.isCorrect());
            answerEntity.setQuestion(questionEntity);
            questionEntity.getAnswers().add(answerEntity);
        }
    }

    /**
     * Gets the quiz for challenge.
     *
     * @param challengeId
     *            the challenge id
     * @return the quiz for challenge
     */
    @Transactional(readOnly = true)
    public QuizSubmitResponse getQuizForChallenge(UUID challengeId) {
        Challenge challenge = challengeRepo.findById(challengeId)
                .orElseThrow(() -> new BusinessException(ErrorConstants.CHALLENGE_NOT_FOUND));

        String quizId = challenge.getVerificationId();
        QuizVerification quizVer = quizVerificationRepo.findById(quizId)
                .orElseThrow(() -> new BusinessException(
                        ErrorConstants.QUIZ_NOT_FOUND));
        return VerificationMapper.mapQuizVerificationToSubmitResponse(quizVer);
    }

    /**
     * Update quiz verification.
     *
     * @param updateDTO the update DTO
     * @return the quiz submit response
     */
    @Transactional
    public QuizSubmitResponse updateQuizVerification(
            QuizSubmitRequest updateDTO) {
        QuizVerification quizVerification =
                quizVerificationRepo.findById(updateDTO.getQuizId())
                        .orElseThrow(() -> new BusinessException(
                                ErrorConstants.QUIZ_NOT_FOUND));

        // Actualizar preguntas
        for (QuestionsDTO questionDTO : updateDTO.getQuestions()) {
            QuizQuestion question =
                    quizQuestionRepo.findById(questionDTO.getQuestionId())
                            .orElseThrow(() -> new BusinessException(
                                    ErrorConstants.QUESTION_NOT_FOUND));
            question.setTitle(questionDTO.getQuestion());
            updateAnswers(questionDTO.getAnswers(), question);
            quizQuestionRepo.save(question);
        }

        // Guardar cambios en QuizVerification
        quizVerificationRepo.save(quizVerification);

        // Crear y devolver QuizSubmitResponse
        return createQuizSubmitResponse(quizVerification);
    }

    /**
     * Update answers.
     *
     * @param answerDTOs the answer DT os
     * @param question the question
     */
    private void updateAnswers(List<AnswerDTO> answerDTOs,
            QuizQuestion question) {
        for (AnswerDTO answerDTO : answerDTOs) {
            QuizAnswer answer = quizAnswerRepo.findById(answerDTO.getAnswerId())
                    .orElseThrow(() -> new BusinessException(
                            ErrorConstants.ANSWER_NOT_FOUND));
            answer.setText(answerDTO.getResult());
            answer.setCorrect(answerDTO.isCorrect());
            quizAnswerRepo.save(answer);
        }
    }

    /**
     * Creates the quiz submit response.
     *
     * @param quizVerification the quiz verification
     * @return the quiz submit response
     */
    private QuizSubmitResponse createQuizSubmitResponse(
            QuizVerification quizVerification) {
        QuizSubmitResponse response = new QuizSubmitResponse();
        response.setQuizId(quizVerification.getId());
        response.setTotalQuestions(quizVerification.getQuestions().size());

        // Inicializar correctAnswers y score
        response.setCorrectAnswers(0); // Inicialmente 0, se puede calcular más
                                       // adelante
        response.setScore(0.0); // Inicialmente 0.0, se puede calcular más
                                // adelante

        // Crear un mapa para los resultados de las preguntas
        Map<String, Boolean> questionResults = new HashMap<>();
        for (QuizQuestion question : quizVerification.getQuestions()) {
            for (QuizAnswer answer : question.getAnswers()) {
                // Aquí se puede agregar lógica para determinar si la respuesta
                // es correcta
                questionResults.put(question.getId(), answer.isCorrect());
            }
        }
        response.setQuestionResults(questionResults);

        return response;
    }

    /**
     * Attempt challenge.
     *
     * @param userChallengeId the user challenge id
     * @param answers the answers
     */
    @Transactional
    public void attemptChallenge(UUID challengeId, UUID userId, List<AnswerDTO> answers) {
        // Buscar el UserChallenge por userId y challengeId
        UserChallenge userChallenge = userChallengeRepository.findByUserIdAndChallengeId(userId, challengeId)
                .orElseThrow(() -> new BusinessException("User challenge not found"));

        // Verificar si ha pasado un día desde que se unió
        if (new Date().getTime() - userChallenge.getJoinedAt().getTime() > 86400000) {
            if (userChallenge.getAttempts() >= 2) {
                throw new BusinessException("No more attempts allowed");
            }
        }
        // Lógica para evaluar las respuestas
        int correctAnswers = evaluateAnswers(answers, userChallenge.getChallenge());
        double scorePercentage = (double) correctAnswers / answers.size();

        if (scorePercentage >= 0.7) {
            userChallenge.setCompleted(true);
            // Aquí puedes agregar lógica para recompensar al usuario si es necesario
        } else {
            userChallenge.setAttempts(userChallenge.getAttempts() + 1);
            if (userChallenge.getAttempts() >= 2) {
                // Penalizar al usuario
                int newScore = userChallenge.getUser().getPoints() - 100;
                userChallenge.getUser().setPoints(Math.max(newScore, 0)); // No permitir que los puntos sean negativos
            }
        }

        userChallengeRepository.save(userChallenge);
    }

    /**
     * Evaluate answers.
     *
     * @param answers the answers
     * @return the int
     */
    private int evaluateAnswers(List<AnswerDTO> answers, Challenge challenge) {
        // Implementa la lógica para contar las respuestas correctas
        int correctCount = 0;
        List<QuizAnswer> correctAnswers = getCorrectAnswers(
                challenge.getVerificationType(), challenge.getVerificationId());
        // Comparar las respuestas del usuario con las respuestas correctas
        for (AnswerDTO userAnswer : answers) {
            for (QuizAnswer correctAnswer : correctAnswers) {
                if (userAnswer.getAnswerId().equals(correctAnswer.getId())
                        && userAnswer.isCorrect() == correctAnswer
                                .isCorrect()) {
                    correctCount++;
                }
            }
        }
        return correctCount;
    }

    /**
     * Gets the correct answers.
     *
     * @param challengeType
     *            the challenge type
     * @param verificationNumber
     *            the verification number
     * @return the correct answers
     */
    private List<QuizAnswer> getCorrectAnswers(String challengeType,
            String verificationNumber) {
        // Combinar el tipo de verificación y el número de verificación
        String fullVerificationId = challengeType + verificationNumber;
        // Obtener el QuizVerification correspondiente
        QuizVerification quizVerification = quizVerificationRepo
                .findById(fullVerificationId)
                .orElseThrow(() -> new BusinessException("Quiz not found"));
        // Obtener las preguntas del quiz
        List<QuizQuestion> questions = quizVerification.getQuestions();
        // Obtener todas las respuestas correctas
        List<QuizAnswer> correctAnswers = new ArrayList<>();
        for (QuizQuestion question : questions) {
            for (QuizAnswer answer : question.getAnswers()) {
                if (answer.isCorrect()) {
                    correctAnswers.add(answer);
                }
            }
        }
        return correctAnswers;
    }
}
