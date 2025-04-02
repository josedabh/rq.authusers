package com.rq.manager.authusers.service;

import org.springframework.stereotype.Service;

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
	 */
	public void createReward() {
		rewardRepository.save(null);
	}
	
	/**
	 * List rewards.
	 */
	public void listRewards() {
		rewardRepository.findAll();
	}
	
	/**
	 * Update reward.
	 */
	public void updateReward() {
		rewardRepository.save(null);
	}
	
	/**
	 * Delete reward.
	 */
	public void deleteReward() {
		rewardRepository.delete(null);
	}

}
