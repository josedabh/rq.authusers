package com.rq.manager.authusers.repository.entity;

import java.time.LocalDateTime;

import com.rq.manager.authusers.enumerations.UserChallengeStateEnum;
import com.rq.manager.authusers.util.TsidUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class UserChallenge — records a user's participation in a challenge.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERCHALLENGE",
        uniqueConstraints = @UniqueConstraint(columnNames = {"USER_ID", "CHALLENGE_ID"}))
public class UserChallenge {

    /** The id (TSID — time-sorted Long). */
    @Id
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    /** The user. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    /** The challenge. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHALLENGE_ID", nullable = false)
    private Challenge challenge;

    /** Date/time at which the challenge was completed (null while in progress). */
    @Column(name = "COMPLETED_AT")
    private LocalDateTime completedAt;

    /** Date/time at which the user joined the challenge. */
    @Column(name = "JOINED_AT", nullable = false)
    private LocalDateTime joinedAt;

    /** Points earned by completing this challenge. */
    @Column(name = "EARNED_POINTS")
    private Integer earnedPoints;

    /** Number of submission attempts. */
    @Column(name = "ATTEMPTS")
    private int attempts;

    /** Lifecycle state of this participation. */
    @Column(name = "STATE", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private UserChallengeStateEnum state;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = TsidUtil.generate();
        }
    }
}

