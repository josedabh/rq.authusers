package com.rq.manager.authusers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rq.manager.authusers.entity.PurchaseHistory;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {

	@Query(value = "SELECT reward_id, COUNT(*) as purchase_count FROM purchase_history "
			+ "GROUP BY reward_id ORDER BY purchase_count DESC LIMIT 10", nativeQuery = true)
	List<Object[]> findTopRewards();

}
