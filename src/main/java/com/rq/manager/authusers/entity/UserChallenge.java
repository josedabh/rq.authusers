package com.rq.manager.authusers.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class UserChallenge 
 * is the interaction of the user when it play the challenge.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERCHALLENGE",
uniqueConstraints = @UniqueConstraint(columnNames = {"USER_ID", "CHALLENGE_ID"}))
public class UserChallenge {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    /** The completed at. */
    // Fecha y hora en que se completó el reto (si aún está en curso, puede ser nulo)
    @Column(name = "COMPLETED_AT")
    private LocalDateTime completedAt;
    
    /** The joined at. */
    @Column(name = "JOINED_AT")
    private LocalDateTime joinedAt;
    
    /** The earned points. */
    @Column(name = "EARNED_POINTS")
    private Integer earnedPoints;
    
    /** The attempts. */
    @Column(name = "ATTEMPTS")
    private int attempts;
    
    /** The progress of the challenges. */
    @Column(name = "COMPLETED", nullable = false)
    private boolean completed;

}
