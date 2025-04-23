package com.rq.manager.authusers.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rq.manager.authusers.bean.RewardRequest;
import com.rq.manager.authusers.bean.RewardResponse;
import com.rq.manager.authusers.entity.Reward;
import com.rq.manager.authusers.mapper.StoreMapper;
import com.rq.manager.authusers.repository.RewardRepository;

import lombok.AllArgsConstructor;

/**
 * The Class StoreService.
 */
@Service
@AllArgsConstructor
public class StoreService {
	
	/** The reward repository. */
	private RewardRepository rewardRepository;
	
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

}
