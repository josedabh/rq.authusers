package com.rq.manager.authusers.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.rq.manager.authusers.enumerations.CategoryEnum;
import com.rq.manager.authusers.enumerations.StatesChallengeEnum;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class Challenge.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CHALLENGE")
public class Challenge {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID id;

    /** The title. */
    @Column(name = "TITLE", nullable = false)
    private String title;

    /** The description. */
    @Column(name = "DESCRIPTION")
    private String description;

    /** The difficulty. */
    @Column(name = "DIFFICULTY", nullable = false)
    private String difficulty;

    /** The category. */
    @Column(name = "CATEGORY", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    /** The state. */
    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    private StatesChallengeEnum state;

    /** The start date. */
    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    /** The end date. */
    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    /** The points. */
    @Column(name = "POINTS", nullable = false)
    private int points;

    /** Tipo de verificación: I = Image, L = Location, Q = Quiz. */
    @Column(name = "VERIFICATION_TYPE", length = 1)
    private String verificationType;

    /**
     * Identificador de la entidad de verificación
     * (p.ej. Q00001, I00002, L00003)
     */
    @Column(name = "VERIFICATION_ID")
    private String verificationId;
}
