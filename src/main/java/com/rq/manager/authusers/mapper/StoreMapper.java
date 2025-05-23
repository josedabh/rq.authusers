package com.rq.manager.authusers.mapper;

import com.rq.manager.authusers.bean.HistoryShopping;
import com.rq.manager.authusers.bean.admin.RewardRequest;
import com.rq.manager.authusers.bean.admin.RewardResponse;
import com.rq.manager.authusers.entity.PurchaseHistory;
import com.rq.manager.authusers.entity.Reward;

public class StoreMapper {
	
    /**
     * Map reward request to entity.
     *
     * @param rewardRequest
     *            the reward request
     * @return the reward
     */
	public static Reward mapRewardRequestToEntity(RewardRequest rewardRequest) {
		Reward reward = new Reward();
		reward.setName(rewardRequest.getName());
		reward.setDescription(rewardRequest.getDescription());
		reward.setPoints(rewardRequest.getPoints());
		reward.setImage(rewardRequest.getImage());
		reward.setVisible(rewardRequest.isVisible());
		reward.setStock(rewardRequest.getStock());
		return reward;
	}
	
    /**
     * Map reward entity to response.
     *
     * @param reward
     *            the reward
     * @return the reward response
     */
	public static RewardResponse mapRewardEntityToResponse(Reward reward) {
		return RewardResponse.builder().id(reward.getId()).name(reward.getName())
				.description(reward.getDescription())
				.points(reward.getPoints())
				.image(reward.getImage()).visible(reward.isVisible())
				.stock(reward.getStock())
				.build();
	}
	
	public static HistoryShopping mapPurchaseHistoryToResponse(PurchaseHistory purchaseHistory) {
		return HistoryShopping.builder().id(purchaseHistory.getId()).userId(String.valueOf(purchaseHistory.getUserId()))
				.productId(purchaseHistory.getRewardId()).totalPrice(purchaseHistory.getPointsSpent())
				.purchaseDate(purchaseHistory.getPurchaseDate().toString()).build();
	}

}
