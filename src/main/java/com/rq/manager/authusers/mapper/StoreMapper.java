package com.rq.manager.authusers.mapper;

import com.rq.manager.authusers.bean.RewardRequest;
import com.rq.manager.authusers.bean.RewardResponse;
import com.rq.manager.authusers.entity.Reward;

public class StoreMapper {
	
	/**
	 * Map reward request to entity.
	 *
	 * @param rewardRequest the reward request
	 * @return the reward
	 */
	public static Reward mapRewardRequestToEntity(RewardRequest rewardRequest) {
		Reward reward = new Reward();
		reward.setName(rewardRequest.getName());
		reward.setDescription(rewardRequest.getDescription());
		reward.setPoints(rewardRequest.getPoints());
		reward.setImage(rewardRequest.getImage());
		reward.setActive(rewardRequest.isActive());
		reward.setStock(rewardRequest.getStock());
		return reward;
	}
	
	public static RewardResponse mapRewardEntityToResponse(Reward reward) {
		return RewardResponse.builder().id(reward.getId()).name(reward.getName())
				.description(reward.getDescription())
				.points(reward.getPoints())
				.image(reward.getImage()).active(reward.isActive())
				.stock(reward.getStock())
				.build();
	}

}
