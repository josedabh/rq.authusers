package com.rq.manager.authusers.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.rq.manager.authusers.bean.admin.UserAnswerDTO;
import com.rq.manager.authusers.enumerations.ChallengeVerificationType;
import com.rq.manager.authusers.enumerations.DifficultyEnum;
import com.rq.manager.authusers.enumerations.RolEnum;
import com.rq.manager.authusers.enumerations.StatesChallengeEnum;
import com.rq.manager.authusers.enumerations.UserChallengeStateEnum;
import com.rq.manager.authusers.exceptions.BusinessException;
import com.rq.manager.authusers.repository.ChallengeRepository;
import com.rq.manager.authusers.repository.QuizAnswerRepository;
import com.rq.manager.authusers.repository.QuizQuestionRepository;
import com.rq.manager.authusers.repository.QuizVerificationRepository;
import com.rq.manager.authusers.repository.UserChallengeRepository;
import com.rq.manager.authusers.repository.UserRepository;
import com.rq.manager.authusers.repository.entity.Challenge;
import com.rq.manager.authusers.repository.entity.QuizAnswer;
import com.rq.manager.authusers.repository.entity.QuizQuestion;
import com.rq.manager.authusers.repository.entity.QuizVerification;
import com.rq.manager.authusers.repository.entity.User;
import com.rq.manager.authusers.repository.entity.UserChallenge;

@ExtendWith(MockitoExtension.class)
class VerificationServiceTest {

    @Mock
    private ChallengeRepository challengeRepo;

    @Mock
    private QuizVerificationRepository quizVerificationRepo;

    @Mock
    private QuizQuestionRepository quizQuestionRepo;

    @Mock
    private QuizAnswerRepository quizAnswerRepo;

    @Mock
    private UserChallengeRepository userChallengeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private VerificationService verificationService;

    private User testUser;
    private Challenge challenge;
    private UserChallenge userChallenge;
    private QuizVerification quizVerification;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("tester");
        testUser.setRol(RolEnum.NORMAL);
        testUser.setPoints(0);

        challenge = new Challenge();
        challenge.setId(10L);
        challenge.setState(StatesChallengeEnum.IN_PROGRESS);
        challenge.setDifficulty(DifficultyEnum.BEGINNER);
        challenge.setVerificationType(ChallengeVerificationType.QUIZ);
        challenge.setVerificationId("00001");
        challenge.setPoints(100);

        userChallenge = new UserChallenge();
        userChallenge.setId(50L);
        userChallenge.setUser(testUser);
        userChallenge.setChallenge(challenge);
        userChallenge.setAttempts(0);
        userChallenge.setState(UserChallengeStateEnum.JOINED);
        userChallenge.setJoinedAt(LocalDateTime.now());

        // Build a quiz with one question and one correct answer
        QuizAnswer correctAnswer = new QuizAnswer();
        correctAnswer.setId("Q00001-P01-R01");
        correctAnswer.setCorrect(true);
        correctAnswer.setText("Correct");

        QuizQuestion question = new QuizQuestion();
        question.setId("Q00001-P01");
        question.setTitle("What is 2+2?");
        question.setAnswers(new ArrayList<>(List.of(correctAnswer)));
        correctAnswer.setQuestion(question);

        quizVerification = new QuizVerification();
        quizVerification.setId("Q00001");
        quizVerification.setChallenge(challenge);
        quizVerification.setQuestions(new ArrayList<>(List.of(question)));
    }

    private void setupSecurityContext() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("tester");
        when(userRepository.findByUsername("tester")).thenReturn(Optional.of(testUser));
    }

    // --- attemptChallenge: COMPLETED path ---

    @Test
    void attemptChallenge_whenAllCorrect_setsStateToCompleted() {
        challenge.setQuestionsCount(1);
        setupSecurityContext();
        when(userChallengeRepository.findByUserIdAndChallengeId(1L, 10L))
                .thenReturn(Optional.of(userChallenge));
        when(quizVerificationRepo.findById("Q00001")).thenReturn(Optional.of(quizVerification));

        UserAnswerDTO answer = new UserAnswerDTO();
        answer.setQuestionId("Q00001-P01");
        answer.setAnswerId("Q00001-P01-R01");

        verificationService.attemptChallenge(10L, List.of(answer));

        assertThat(userChallenge.getState()).isEqualTo(UserChallengeStateEnum.COMPLETED);
        assertThat(testUser.getPoints()).isEqualTo(100);
        verify(userChallengeRepository).save(userChallenge);
    }

    // --- attemptChallenge: IN_PROGRESS path (1st failure) ---

    @Test
    void attemptChallenge_whenFirstFailure_setsStateToInProgress() {
        challenge.setQuestionsCount(1);
        setupSecurityContext();
        when(userChallengeRepository.findByUserIdAndChallengeId(1L, 10L))
                .thenReturn(Optional.of(userChallenge));
        when(quizVerificationRepo.findById("Q00001")).thenReturn(Optional.of(quizVerification));

        // Submit wrong answer
        UserAnswerDTO wrong = new UserAnswerDTO();
        wrong.setQuestionId("Q00001-P01");
        wrong.setAnswerId("WRONG_ID");

        verificationService.attemptChallenge(10L, List.of(wrong));

        assertThat(userChallenge.getState()).isEqualTo(UserChallengeStateEnum.IN_PROGRESS);
        assertThat(userChallenge.getAttempts()).isEqualTo(1);
    }

    // --- attemptChallenge: FAILED path (2nd failure) ---

    @Test
    void attemptChallenge_whenSecondFailure_setsStateToFailedAndDeductsPoints() {
        challenge.setQuestionsCount(1);
        userChallenge.setAttempts(1);
        testUser.setPoints(200);
        setupSecurityContext();
        when(userChallengeRepository.findByUserIdAndChallengeId(1L, 10L))
                .thenReturn(Optional.of(userChallenge));
        when(quizVerificationRepo.findById("Q00001")).thenReturn(Optional.of(quizVerification));

        UserAnswerDTO wrong = new UserAnswerDTO();
        wrong.setQuestionId("Q00001-P01");
        wrong.setAnswerId("WRONG_ID");

        verificationService.attemptChallenge(10L, List.of(wrong));

        assertThat(userChallenge.getState()).isEqualTo(UserChallengeStateEnum.FAILED);
        assertThat(testUser.getPoints()).isEqualTo(100); // 200 - 100
    }

    // --- attemptChallenge: challenge not found ---

    @Test
    void attemptChallenge_whenUserChallengeNotFound_throwsBusinessException() {
        setupSecurityContext();
        when(userChallengeRepository.findByUserIdAndChallengeId(1L, 10L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> verificationService.attemptChallenge(10L, List.of()))
                .isInstanceOf(BusinessException.class);
    }
}
