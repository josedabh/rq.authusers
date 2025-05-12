package com.rq.manager.authusers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rq.manager.authusers.entity.PurchaseHistory;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long>{

}
