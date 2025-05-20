package com.rq.manager.authusers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rq.manager.authusers.entity.Reward;

public interface RewardRepository extends JpaRepository<Reward, Long> {

    @Query(value = "SELECT * FROM REWARD R WHERE R.VISIBLE = TRUE",
            nativeQuery = true)
    List<Reward> findAllVisible();
}
