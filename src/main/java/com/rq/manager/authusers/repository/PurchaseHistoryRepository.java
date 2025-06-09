package com.rq.manager.authusers.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rq.manager.authusers.entity.PurchaseHistory;

/**
 * The Interface PurchaseHistoryRepository.
 */
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {

	/**
	 * Find top rewards.
	 *
	 * @return the list
	 */
	@Query(value = "SELECT reward_id, COUNT(*) as purchase_count FROM purchase_history "
			+ "GROUP BY reward_id ORDER BY purchase_count DESC LIMIT 10", nativeQuery = true)
	List<Object[]> findTopRewards();

	/**
	 * Find by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	List<PurchaseHistory> findByUserId(UUID userId);

}
