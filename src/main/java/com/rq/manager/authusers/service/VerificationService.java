package com.rq.manager.authusers.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rq.manager.authusers.bean.admin.AnswerDTO;
import com.rq.manager.authusers.bean.admin.QuestionsDTO;
import com.rq.manager.authusers.bean.admin.QuizAnswerDetail;
import com.rq.manager.authusers.bean.admin.QuizDetailResponse;
import com.rq.manager.authusers.bean.admin.QuizQuestionDetail;
import com.rq.manager.authusers.bean.admin.QuizSubmitRequest;
import com.rq.manager.authusers.bean.admin.QuizSubmitResponse;
import com.rq.manager.authusers.bean.admin.UserAnswerDTO;
import com.rq.manager.authusers.entity.Challenge;
import com.rq.manager.authusers.entity.QuizAnswer;
import com.rq.manager.authusers.entity.QuizQuestion;
import com.rq.manager.authusers.entity.QuizVerification;
import com.rq.manager.authusers.entity.User;
import com.rq.manager.authusers.entity.UserChallenge;
import com.rq.manager.authusers.exceptions.BusinessException;
import com.rq.manager.authusers.exceptions.CustomException;
import com.rq.manager.authusers.exceptions.ErrorConstants;
import com.rq.manager.authusers.repository.ChallengeRepository;
import com.rq.manager.authusers.repository.QuizAnswerRepository;
import com.rq.manager.authusers.repository.QuizQuestionRepository;
import com.rq.manager.authusers.repository.QuizVerificationRepository;
import com.rq.manager.authusers.repository.UserChallengeRepository;
import com.rq.manager.authusers.repository.UserRepository;

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
    
    /** The user repository. */
    private UserRepository userRepository;

    /**
     * Creates the quiz verification.
     *
     * @param request
     *            the request
     * @return the string
     */
    @Transactional
    public String createQuizVerification(QuizSubmitRequest request) {
        // 1) Extraer challengeId del quizId  
        //    ej. quizId = "Q00005" → challenge.getVerificationType()="Q", challenge.getVerificationId()="00005"
        String quizId = request.getQuizId();
        String type = quizId.substring(0, 1);
        String numeric = quizId.substring(1);

        // 2) Recuperar el Challenge por type+numeric
        Challenge challenge = challengeRepo
            .findByVerificationTypeAndVerificationId(type, numeric)
            .orElseThrow(() -> new BusinessException(ErrorConstants.CHALLENGE_NOT_FOUND));

        // 3) Crear el QuizVerification
        QuizVerification quiz = new QuizVerification();
        quiz.setId(quizId);
        quiz.setChallenge(challenge);

        // 4) Mapear preguntas y respuestas
        mapQuestionsAndAnswers(request, quiz, quizId);

        // 5) Persistir todo en cascada
        quizVerificationRepo.save(quiz);

        return quizId;
    }

    /**
     * Map questions and answers.
     *
     * @param request the request
     * @param quiz the quiz
     * @param fullQuizId the full quiz id
     */
    private void mapQuestionsAndAnswers(QuizSubmitRequest request,
                                        QuizVerification quiz,
                                        String fullQuizId) {
        int qCounter = 1;
        for (QuestionsDTO qDto : request.getQuestions()) {
            String qId = (qDto.getQuestionId() != null && !qDto.getQuestionId().isBlank())
                         ? qDto.getQuestionId()
                         : fullQuizId + "-P" + String.format("%02d", qCounter++);
            QuizQuestion questionEntity = new QuizQuestion();
            questionEntity.setId(qId);
            questionEntity.setTitle(qDto.getQuestion());
            questionEntity.setQuiz(quiz);

            int aCounter = 1;
            for (AnswerDTO aDto : qDto.getAnswers()) {
                String aId = (aDto.getAnswerId() != null && !aDto.getAnswerId().isBlank())
                             ? aDto.getAnswerId()
                             : qId + "-R" + String.format("%02d", aCounter++);
                QuizAnswer answerEntity = new QuizAnswer();
                answerEntity.setId(aId);
                answerEntity.setText(aDto.getResult());
                answerEntity.setCorrect(aDto.getIsCorrect());
                answerEntity.setQuestion(questionEntity);
                questionEntity.getAnswers().add(answerEntity);
            }

            quiz.getQuestions().add(questionEntity);
        }
    }

    /**
     * Gets the quiz details for challenge.
     *
     * @param challengeId the challenge id
     * @return the quiz details
     */
    @Transactional(readOnly = true)
    public QuizDetailResponse getQuizDetailsForChallenge(UUID challengeId) {
        Challenge challenge = challengeRepo.findById(challengeId)
                .orElseThrow(() -> new BusinessException(ErrorConstants.CHALLENGE_NOT_FOUND));

        // Check if verification type is 'Q'
        if (!"Q".equals(challenge.getVerificationType())) {
            throw new BusinessException("Quiz only available for verification type Q");
        }

        String quizId = challenge.getVerificationType() + challenge.getVerificationId();
        QuizVerification quizVer = quizVerificationRepo.findById(quizId)
                .orElseThrow(() -> new BusinessException(ErrorConstants.QUIZ_NOT_FOUND));
        
        return mapQuizVerificationToDetailResponse(quizVer);
    }

    /**
     * Maps QuizVerification entity to QuizDetailResponse DTO.
     *
     * @param quizVer the quiz verification entity
     * @return the quiz detail response
     */
    private QuizDetailResponse mapQuizVerificationToDetailResponse(QuizVerification quizVer) {
        QuizDetailResponse response = new QuizDetailResponse();
        response.setQuizId(quizVer.getId());
        
        List<QuizQuestionDetail> questionDTOs = new ArrayList<>();
        for (QuizQuestion question : quizVer.getQuestions()) {
            QuizQuestionDetail qDetail = new QuizQuestionDetail();
            qDetail.setQuestionId(question.getId());
            qDetail.setTitle(question.getTitle());
            
            List<QuizAnswerDetail> answerDTOs = new ArrayList<>();
            for (QuizAnswer answer : question.getAnswers()) {
                QuizAnswerDetail aDetail = new QuizAnswerDetail();
                aDetail.setAnswerId(answer.getId());
                aDetail.setText(answer.getText());
                answerDTOs.add(aDetail);
            }
            
            qDetail.setAnswers(answerDTOs);
            questionDTOs.add(qDetail);
        }
        
        response.setQuestions(questionDTOs);
        return response;
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
            answer.setCorrect(answerDTO.getIsCorrect());
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
                questionResults.put(question.getId(), answer.getCorrect());
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
    public void attemptChallenge(UUID challengeId, List<UserAnswerDTO> userAnswers) {
        User user = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName())
            .orElseThrow(() -> new CustomException(ErrorConstants.NULL_USER));
        // Buscar el UserChallenge por userId y challengeId
        UserChallenge userChallenge = userChallengeRepository.findByUserIdAndChallengeId(user.getId(), challengeId)
                .orElseThrow(() -> new BusinessException("User challenge not found"));

        // Verificar si ha pasado un día desde que se unió
        if (new Date().getTime() - userChallenge.getJoinedAt().getTime() > 86400000) {
            if (userChallenge.getAttempts() >= 2) {
                throw new BusinessException("No more attempts allowed");
            }
        }
        // Lógica para evaluar las respuestas
        int correctAnswers = evaluateAnswers(userAnswers, userChallenge.getChallenge());
        double scorePercentage = (double) correctAnswers / userChallenge.getChallenge().getQuestionsCount();

        if (scorePercentage >= 0.7) {
            userChallenge.setCompleted(true);
            userChallenge.setCompletedAt(new Date());
            userChallenge.setEarnedPoints(userChallenge.getChallenge().getPoints());
            int pointTotal = user.getPoints() + userChallenge.getChallenge().getPoints();
            user.setPoints(pointTotal);
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
     * @param userAnswers the user answers
     * @param challenge the challenge
     * @return the int
     */
    private int evaluateAnswers(List<UserAnswerDTO> userAnswers, Challenge challenge) {
        // Obtener respuestas correctas del quiz
        Map<String, Set<String>> correctAnswers = getCorrectAnswersByQuestion(
                challenge.getVerificationType(), 
                challenge.getVerificationId()
        );
        
        int correctCount = 0;
        
        for (UserAnswerDTO userAnswer : userAnswers) {
            Set<String> correctForQuestion = correctAnswers.get(userAnswer.getQuestionId());
            
            if (correctForQuestion != null && correctForQuestion.contains(userAnswer.getAnswerId())) {
                correctCount++;
            }
        }
        
        return correctCount;
    }

    /**
     * Gets the correct answers by question.
     *
     * @param type the type
     * @param number the number
     * @return the correct answers by question
     */
    private Map<String, Set<String>> getCorrectAnswersByQuestion(String type, String number) {
        String fullId = type + number;
        QuizVerification quiz = quizVerificationRepo.findById(fullId)
                .orElseThrow(() -> new BusinessException("Quiz not found"));
        
        Map<String, Set<String>> correctAnswersMap = new HashMap<>();
        
        for (QuizQuestion question : quiz.getQuestions()) {
            Set<String> correctIds = new HashSet<>();
            
            for (QuizAnswer answer : question.getAnswers()) {
                if (answer.getCorrect()) {
                    correctIds.add(answer.getId());
                }
            }
            
            correctAnswersMap.put(question.getId(), correctIds);
        }
        
        return correctAnswersMap;
    }

}
