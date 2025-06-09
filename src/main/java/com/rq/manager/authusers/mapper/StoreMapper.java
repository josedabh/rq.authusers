package com.rq.manager.authusers.mapper;

import com.rq.manager.authusers.bean.HistoryShopping;
import com.rq.manager.authusers.bean.admin.RewardRequest;
import com.rq.manager.authusers.bean.admin.RewardResponse;
import com.rq.manager.authusers.entity.PurchaseHistory;
import com.rq.manager.authusers.entity.Reward;
import com.rq.manager.authusers.entity.User;

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
				.visible(reward.isVisible())
				.stock(reward.getStock())
				.build();
	}

    /**
     * Map to history shopping.
     *
     * @param ph
     *            the ph
     * @param user
     *            the user
     * @param reward
     *            the reward
     * @param pointsBefore
     *            the points before
     * @return the history shopping
     */
    public static HistoryShopping mapToHistoryShopping(PurchaseHistory ph,
            User user, Reward reward) {
        return HistoryShopping.builder().transactionId(ph.getId())
                .userName(user.getName())
                .userLastname(user.getLastname()).userUsername(user.getUsername())
                .pointsAfter(ph.getPointsAfter())
                .purchaseDate(ph.getPurchaseDate().toString())
                .rewardName(reward.getName())
                .rewardDescription(reward.getDescription())
                .rewardPoints(reward.getPoints()).build();
    }

    /**
     * Map purchase history to response.
     *
     * @param ph
     *            the ph
     * @return the history shopping
     */
    public static HistoryShopping mapPurchaseHistoryToResponse(PurchaseHistory ph) {
        return HistoryShopping.builder()
            .transactionId(ph.getId())
            .userName(ph.getUser().getName())
            .userLastname(ph.getUser().getLastname())
            .userUsername(ph.getUser().getUsername()) 
            .purchaseDate(ph.getPurchaseDate().toString())
            .rewardName(ph.getReward().getName())
            .rewardDescription(ph.getReward().getDescription())
            .rewardPoints(ph.getReward().getPoints())
            .pointsBefore(ph.getPointsBefore())
            .pointsAfter(ph.getPointsAfter())
            .build();
    }

}
