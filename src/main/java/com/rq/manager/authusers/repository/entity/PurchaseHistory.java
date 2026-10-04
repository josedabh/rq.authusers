package com.rq.manager.authusers.repository.entity;

import java.time.LocalDateTime;

import com.rq.manager.authusers.util.TsidUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class PurchaseHistory.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PURCHASE_HISTORY")
public class PurchaseHistory {

    /** The id (TSID — time-sorted Long). */
    @Id
    @Column(name = "ID")
    private Long id;

    /** User who made the purchase. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Reward that was purchased. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reward_id", nullable = false)
    private Reward reward;

    /** Exact date/time of the purchase. */
    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime purchaseDate;

    /** User points before the purchase. */
    @Column(name = "points_before", nullable = false)
    private Integer pointsBefore;

    /** User points after the purchase. */
    @Column(name = "points_after", nullable = false)
    private Integer pointsAfter;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = TsidUtil.generate();
        }
    }
}
