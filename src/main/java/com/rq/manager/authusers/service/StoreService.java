/*
 * 
 */
package com.rq.manager.authusers.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rq.manager.authusers.bean.admin.RewardRequest;
import com.rq.manager.authusers.bean.admin.RewardResponse;
import com.rq.manager.authusers.bean.admin.UserResponse;
import com.rq.manager.authusers.entity.PurchaseHistory;
import com.rq.manager.authusers.entity.Reward;
import com.rq.manager.authusers.entity.User;
import com.rq.manager.authusers.enumerations.RolEnum;
import com.rq.manager.authusers.exceptions.BusinessException;
import com.rq.manager.authusers.exceptions.CustomException;
import com.rq.manager.authusers.mapper.StoreMapper;
import com.rq.manager.authusers.mapper.UserMapper;
import com.rq.manager.authusers.repository.PurchaseHistoryRepository;
import com.rq.manager.authusers.repository.RewardRepository;
import com.rq.manager.authusers.repository.UserRepository;

import lombok.AllArgsConstructor;

// TODO: Auto-generated Javadoc
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
     * List rewards users.
     *
     * @return the list reward response
     */
    public List<RewardResponse> listRewardsUsers() {
        return rewardRepository.findAllVisible().stream()
                .map(StoreMapper::mapRewardEntityToResponse).toList();
    }

	/**
     * Update reward.
     *
     * @param id
     *            the id
     * @param rewardRequest
     *            the reward request
     * @return the reward response
     */
    public RewardResponse updateReward(long id, RewardRequest rewardRequest) {
        Reward existingReward = rewardRepository.findById(id)
            .orElse(new Reward());

        // Validación: ¿tiene permiso para modificar?
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser.getRol() == null || !currentUser.getRol().equals(RolEnum.ADMIN)) {
            throw new BusinessException("UNAUTHORIZED_ACCESS");
        }

        // Validación de lógica de negocio
        if (rewardRequest.getPoints() != null && rewardRequest.getPoints() < 0) {
            throw new BusinessException("POINTS_CANNOT_BE_NEGATIVE");
        }
        
		if (rewardRequest.getStock() != null && rewardRequest.getStock() < 0) {
			throw new BusinessException("STOCK_CANNOT_BE_NEGATIVE");
		}
        // Actualización parcial (tipo PATCH)
        if (rewardRequest.getName() != null) {
            existingReward.setName(rewardRequest.getName());
        }

        if (rewardRequest.getDescription() != null) {
            existingReward.setDescription(rewardRequest.getDescription());
        }
        if (rewardRequest.getImage() != null) {
            existingReward.setImage(rewardRequest.getImage());
        }

        // Guardar cambios
        rewardRepository.save(existingReward);

        return StoreMapper.mapRewardEntityToResponse(existingReward);
    }


    /**
     * Delete reward.
     *
     * @param id
     *            the id
     */
    public void deleteReward(long id) {
		rewardRepository.deleteById(id);
	}

    /**
     * Gets the reward by id.
     *
     * @param id
     *            the id
     * @return the reward by id
     */
    public RewardResponse getRewardById(long id) {
        Reward reward = rewardRepository.findById(id).orElse(new Reward());
        return StoreMapper.mapRewardEntityToResponse(reward);
    }

    /**
     * Buy reward.
     *
     * @param rewardId
     *            the reward id
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
        PurchaseHistory purchase = createPurchaseHistory(user.getId(), rewardId,
                reward.getPoints());
        // 5. Guardar cambios en la base de datos
        rewardRepository.save(reward);
        userRepository.save(UserMapper.mapUserResponseToEntity(user));
        purchaseHistoryRepository.save(purchase);
        return StoreMapper.mapRewardEntityToResponse(reward);
    }

    /**
     * Creates a purchase history record.
     *
     * @param userId
     *            the user ID
     * @param rewardId
     *            the reward ID
     * @param pointsSpent
     *            the points spent
     * @return the purchase history
     */
    private PurchaseHistory createPurchaseHistory(String userId, Long rewardId,
            Integer pointsSpent) {
        PurchaseHistory purchase = new PurchaseHistory();
        purchase.setUserId(UUID.fromString(userId));
        purchase.setRewardId(rewardId);
        purchase.setPointsSpent(pointsSpent);
        purchase.setPurchaseDate(LocalDateTime.now());
		return purchase;
	}

	/**
	 * Gets the top rewards based on purchase count.
	 *
	 * @param id the id
	 * @param stock the stock
	 * @return the list of most purchased rewards
	 */
//	public List<RewardResponse> getTopRewards() {
//		// Obtener los IDs de los rewards más comprados y su cantidad de compras
//		List<Object[]> topRewardIds = purchaseHistoryRepository.findTopRewards();
//
//		// Obtener los rewards completos y mapearlos a RewardResponse
//		return topRewardIds.stream().map(result -> {
//			Long rewardId = (Long) result[0];
//			Long purchaseCount = (Long) result[1];
//			Reward reward = rewardRepository.findById(rewardId)
//					.orElseThrow(() -> new CustomException("Reward not found"));
//			RewardResponse response = StoreMapper.mapRewardEntityToResponse(reward);
//			response.setPurchaseCount(purchaseCount.intValue());
//			return response;
//		}).toList();
//	}
	
	/**
	 * Adds the reward stock.
	 *
	 * @param id the id
	 * @param stock the stock
	 * @return the reward response
	 */
    public RewardResponse addRewardStock(long id, int stock) {
        Reward reward = rewardRepository.findById(id)
                .orElseThrow(() -> new CustomException("No hay recompensa"));
        reward.setStock(reward.getStock() + stock);
        rewardRepository.save(reward);
        return StoreMapper.mapRewardEntityToResponse(reward);
    }

    /**
     * Toggle reward visibility.
     *
     * @param id
     *            the id
     * @return the reward response
     */
	public void toggleRewardVisibility(long id) {
	    Reward reward = rewardRepository.findById(id)
	        .orElseThrow(() -> new CustomException("No hay recompensa"));
	    //Cambiamos la visibilidad
	    reward.setVisible(!reward.isVisible());
	    rewardRepository.save(reward);
	}
}
