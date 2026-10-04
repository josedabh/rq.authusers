package com.rq.manager.authusers.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.rq.manager.authusers.bean.admin.RewardRequest;
import com.rq.manager.authusers.bean.admin.RewardResponse;
import com.rq.manager.authusers.enumerations.RolEnum;
import com.rq.manager.authusers.exceptions.ResourceNotFoundException;
import com.rq.manager.authusers.repository.PurchaseHistoryRepository;
import com.rq.manager.authusers.repository.RewardRepository;
import com.rq.manager.authusers.repository.UserRepository;
import com.rq.manager.authusers.repository.entity.Reward;
import com.rq.manager.authusers.repository.entity.User;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    private RewardRepository rewardRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PurchaseHistoryRepository purchaseHistoryRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private StoreService storeService;

    private User testUser;
    private Reward testReward;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setRol(RolEnum.NORMAL);
        testUser.setPoints(200);

        testReward = new Reward();
        testReward.setId(100L);
        testReward.setName("Test Reward");
        testReward.setPoints(50);
        testReward.setStock(10);
        testReward.setVisible(true);
    }

    // --- createReward ---

    @Test
    void createReward_savesAndReturnsResponse() {
        RewardRequest request = new RewardRequest();
        request.setName("New Reward");
        request.setPoints(30);
        request.setStock(5);
        request.setVisible(true);

        when(rewardRepository.save(any(Reward.class))).thenAnswer(inv -> inv.getArgument(0));

        RewardResponse response = storeService.createReward(request);

        assertThat(response).isNotNull();
        verify(rewardRepository).save(any(Reward.class));
    }

    // --- listRewards ---

    @Test
    void listRewards_returnsAllRewards() {
        when(rewardRepository.findAll()).thenReturn(List.of(testReward));

        List<RewardResponse> result = storeService.listRewards();

        assertThat(result).hasSize(1);
    }

    // --- listRewardsUsers ---

    @Test
    void listRewardsUsers_returnsOnlyVisibleRewards() {
        Reward invisible = new Reward();
        invisible.setId(200L);
        invisible.setVisible(false);

        when(rewardRepository.findAll()).thenReturn(List.of(testReward, invisible));

        List<RewardResponse> result = storeService.listRewardsUsers();

        assertThat(result).hasSize(1);
    }

    // --- buyReward ---

    @Test
    void buyReward_whenNoStock_throwsException() {
        testReward.setStock(0);
        when(rewardRepository.findById(100L)).thenReturn(Optional.of(testReward));

        assertThatThrownBy(() -> storeService.buyReward(100L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void buyReward_whenNotEnoughPoints_throwsException() {
        testUser.setPoints(10);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(rewardRepository.findById(100L)).thenReturn(Optional.of(testReward));
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        assertThatThrownBy(() -> storeService.buyReward(100L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void buyReward_whenSufficientPoints_deductsPointsAndSaves() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(rewardRepository.findById(100L)).thenReturn(Optional.of(testReward));
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(rewardRepository.findById(100L)).thenReturn(Optional.of(testReward));

        storeService.buyReward(100L);

        assertThat(testUser.getPoints()).isEqualTo(150);
        assertThat(testReward.getStock()).isEqualTo(9);
        verify(purchaseHistoryRepository).save(any());
    }

    // --- toggleRewardVisibility ---

    @Test
    void toggleRewardVisibility_togglesAndSaves() {
        when(rewardRepository.findById(100L)).thenReturn(Optional.of(testReward));

        storeService.toggleRewardVisibility(100L);

        assertThat(testReward.isVisible()).isFalse();
        verify(rewardRepository).save(testReward);
    }

    // --- addRewardStock ---

    @Test
    void addRewardStock_increasesStockAndSaves() {
        when(rewardRepository.findById(100L)).thenReturn(Optional.of(testReward));

        RewardResponse result = storeService.addRewardStock(100L, 5);

        assertThat(testReward.getStock()).isEqualTo(15);
        assertThat(result).isNotNull();
        verify(rewardRepository).save(testReward);
    }
}
