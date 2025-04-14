package com.rq.manager.authusers.entity;

import java.util.Date;
import java.util.List;

import com.rq.manager.authusers.bean.HistoryChallenges;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "USERCHALLENGE")
public class UserChallenge {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    /** The user. */
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    /** The challenge. */
    @ManyToOne
    @JoinColumn(name = "CHALLENGE_ID", nullable = false)
    private Challenge challenge;
    
    /** The completed at. */
    // Fecha y hora en que se completó el reto (si aún está en curso, puede ser nulo)
    @Column(name = "COMPLETED_AT")
    private Date completedAt;
    
    /** The earned points. */
    @Column(name = "EARNED_POINTS")
    private Integer earnedPoints;
    
    /** The progress of the challenges. */
    @Column(name = "PROGRESS", nullable = false)
    private int progress;
    
    /** The history of the challenges completed of the user. */
    //Cambiar esto por una entidad
    @OneToMany(mappedBy = "userChallenge")
    private List<HistoryChallenges> history;

}
