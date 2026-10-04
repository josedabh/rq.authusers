package com.rq.manager.authusers.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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

import com.rq.manager.authusers.bean.admin.ChallengeResponse;
import com.rq.manager.authusers.enumerations.DifficultyEnum;
import com.rq.manager.authusers.enumerations.RolEnum;
import com.rq.manager.authusers.enumerations.StatesChallengeEnum;
import com.rq.manager.authusers.enumerations.UserChallengeStateEnum;
import com.rq.manager.authusers.exceptions.BusinessException;
import com.rq.manager.authusers.exceptions.ResourceNotFoundException;
import com.rq.manager.authusers.repository.ChallengeRepository;
import com.rq.manager.authusers.repository.QuizVerificationRepository;
import com.rq.manager.authusers.repository.UserChallengeRepository;
import com.rq.manager.authusers.repository.UserRepository;
import com.rq.manager.authusers.repository.entity.Challenge;
import com.rq.manager.authusers.repository.entity.User;
import com.rq.manager.authusers.repository.entity.UserChallenge;

@ExtendWith(MockitoExtension.class)
class ChallengeServiceTest {

    @Mock
    private ChallengeRepository challengeRepository;

    @Mock
    private UserChallengeRepository userChallengeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuizVerificationRepository quizVerificationRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ChallengeService challengeService;

    private User testUser;
    private Challenge pendingChallenge;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setRol(RolEnum.NORMAL);
        testUser.setPoints(100);

        pendingChallenge = new Challenge();
        pendingChallenge.setId(10L);
        pendingChallenge.setTitle("Test Challenge");
        pendingChallenge.setState(StatesChallengeEnum.PENDING);
        pendingChallenge.setDifficulty(DifficultyEnum.BEGINNER);
        pendingChallenge.setPoints(50);
        pendingChallenge.setStartDate(LocalDateTime.now().plusDays(1));
        pendingChallenge.setEndDate(LocalDateTime.now().plusDays(30));
    }

    // --- getChallengeById ---

    @Test
    void getChallengeById_whenFound_returnsChallengeResponse() {
        when(challengeRepository.findById(10L)).thenReturn(Optional.of(pendingChallenge));

        ChallengeResponse response = challengeService.getChallengeById(10L);

        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("Test Challenge");
    }

    @Test
    void getChallengeById_whenNotFound_throwsResourceNotFoundException() {
        when(challengeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> challengeService.getChallengeById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    // --- deleteChallenge ---

    @Test
    void deleteChallenge_whenPending_deletesSuccessfully() {
        when(challengeRepository.findById(10L)).thenReturn(Optional.of(pendingChallenge));

        challengeService.deleteChallenge(10L);

        verify(challengeRepository).deleteById(10L);
    }

    @Test
    void deleteChallenge_whenInProgress_throwsBusinessException() {
        pendingChallenge.setState(StatesChallengeEnum.IN_PROGRESS);
        when(challengeRepository.findById(10L)).thenReturn(Optional.of(pendingChallenge));

        assertThatThrownBy(() -> challengeService.deleteChallenge(10L))
                .isInstanceOf(BusinessException.class);
    }

    // --- cancelChallenge ---

    @Test
    void cancelChallenge_whenInProgress_cancelsAndReturnsResponse() {
        pendingChallenge.setState(StatesChallengeEnum.IN_PROGRESS);
        when(challengeRepository.findById(10L)).thenReturn(Optional.of(pendingChallenge));

        ChallengeResponse response = challengeService.cancelChallenge(10L);

        assertThat(response).isNotNull();
        verify(challengeRepository).save(pendingChallenge);
    }

    @Test
    void cancelChallenge_whenAlreadyCancelled_throwsBusinessException() {
        pendingChallenge.setState(StatesChallengeEnum.CANCELLED);
        when(challengeRepository.findById(10L)).thenReturn(Optional.of(pendingChallenge));

        assertThatThrownBy(() -> challengeService.cancelChallenge(10L))
                .isInstanceOf(BusinessException.class);
    }

    // --- joinChallenge ---

    @Test
    void joinChallenge_whenValidAndNewUser_createsUserChallengeWithJoinedState() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(challengeRepository.findById(10L)).thenReturn(Optional.of(pendingChallenge));
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(userChallengeRepository.findByUserAndChallenge(testUser, pendingChallenge))
                .thenReturn(Optional.empty());

        challengeService.joinChallenge(10L);

        verify(userChallengeRepository).save(any(UserChallenge.class));
    }

    @Test
    void joinChallenge_whenAlreadyJoined_throwsBusinessException() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(challengeRepository.findById(10L)).thenReturn(Optional.of(pendingChallenge));
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        UserChallenge existing = new UserChallenge();
        existing.setState(UserChallengeStateEnum.JOINED);
        existing.setAttempts(1);
        when(userChallengeRepository.findByUserAndChallenge(testUser, pendingChallenge))
                .thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> challengeService.joinChallenge(10L))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void joinChallenge_whenChallengeFinished_throwsBusinessException() {
        pendingChallenge.setState(StatesChallengeEnum.FINISHED);
        when(challengeRepository.findById(10L)).thenReturn(Optional.of(pendingChallenge));

        assertThatThrownBy(() -> challengeService.joinChallenge(10L))
                .isInstanceOf(BusinessException.class);
    }

    // --- listChallenges ---

    @Test
    void listChallenges_returnsAllChallenges() {
        when(challengeRepository.findAll()).thenReturn(List.of(pendingChallenge));

        List<ChallengeResponse> result = challengeService.listChallenges();

        assertThat(result).hasSize(1);
    }
}
