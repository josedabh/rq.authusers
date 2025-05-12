package com.rq.manager.authusers.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rq.manager.authusers.bean.RewardRequest;
import com.rq.manager.authusers.bean.RewardResponse;
import com.rq.manager.authusers.bean.admin.UserResponse;
import com.rq.manager.authusers.entity.PurchaseHistory;
import com.rq.manager.authusers.entity.Reward;
import com.rq.manager.authusers.exceptions.CustomException;
import com.rq.manager.authusers.mapper.StoreMapper;
import com.rq.manager.authusers.mapper.UserMapper;
import com.rq.manager.authusers.repository.PurchaseHistoryRepository;
import com.rq.manager.authusers.repository.RewardRepository;
import com.rq.manager.authusers.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 * The Class StoreService.
 */
@Service
@AllArgsConstructor
public class StoreService {
	
	/** The reward repository. */
	private RewardRepository rewardRepository;
	
	/** The auth service. */
	private AuthService authService;
	
	 /** The user repository. */
 	private UserRepository userRepository;
 	
 	/** The purchase history repository. */
	 private PurchaseHistoryRepository purchaseHistoryRepository;
	
	/**
	 * Creates the reward.
	 *
	 * @param rewardRequest the reward request
	 * @return the reward response
	 */
	public RewardResponse createReward(RewardRequest rewardRequest) {
		Reward reward = StoreMapper.mapRewardRequestToEntity(rewardRequest);
		rewardRepository.save(reward);
		return StoreMapper.mapRewardEntityToResponse(reward);
	}
	
	/**
	 * List rewards.
	 *
	 * @return the list reward response
	 */
	public List<RewardResponse> listRewards() {
		return rewardRepository.findAll().stream()
				.map(StoreMapper::mapRewardEntityToResponse).toList();
	}
	
	/**
	 * Update reward.
	 *
	 * @param id the id
	 * @param rewardRequest the reward request
	 * @return the reward response
	 */
	public RewardResponse updateReward(long id, RewardRequest rewardRequest) {
		Reward reward = StoreMapper.mapRewardRequestToEntity(rewardRequest);
		rewardRepository.save(reward);
		return null;
	}
	
	/**
	 * Delete reward.
	 *
	 * @param id the id
	 */
	public void deleteReward(long id) {
		rewardRepository.deleteById(id);
	}
	
	/**
	 * Gets the reward by id.
	 *
	 * @param id the id
	 * @return the reward by id
	 */
	public RewardResponse getRewardById(long id) {
		Reward reward = rewardRepository.findById(id).orElse(new Reward());
		return StoreMapper.mapRewardEntityToResponse(reward);
	}

    /**
     * Buy reward.
     *
     * @param rewardId the reward id
     * @return the reward response
     */
    public RewardResponse buyReward(long rewardId) {
        // 1. Obtener el reward y verificar stock
        Reward reward = rewardRepository.findById(rewardId)
            .orElse(new Reward());
        if (reward.getStock() <= 0) {
            throw new CustomException("No stock available");
        }
        // 2. Obtener el usuario y verificar puntos
        UserResponse user = authService.getUser();
        if (user.getPoints() < reward.getPoints()) {
            throw new CustomException("Not enough points");
        }
        // 3. Actualizar stock y puntos
        reward.setStock(reward.getStock() - 1);
        user.setPoints(user.getPoints() - reward.getPoints());
        // 4. Registrar la transacción
		PurchaseHistory purchase = new PurchaseHistory();
		purchase.setId(1L); // Asignar un ID temporal
		purchase.setUserId(UUID.fromString(user.getId()));
		purchase.setRewardId(rewardId);
		purchase.setPointsSpent(reward.getPoints());
		purchase.setPurchaseDate(LocalDateTime.now());
        // 5. Guardar cambios en la base de datos
        rewardRepository.save(reward);
        userRepository.save(UserMapper.mapUserResponseToEntity(user));
        purchaseHistoryRepository.save(purchase);
        return StoreMapper.mapRewardEntityToResponse(reward);
    }
}
