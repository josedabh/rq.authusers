package com.rq.manager.authusers.repository.entity;

import java.time.LocalDateTime;

import com.rq.manager.authusers.enumerations.CategoryEnum;
import com.rq.manager.authusers.enumerations.ChallengeVerificationType;
import com.rq.manager.authusers.enumerations.DifficultyEnum;
import com.rq.manager.authusers.enumerations.StatesChallengeEnum;
import com.rq.manager.authusers.util.TsidUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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

    /** The id (TSID — time-sorted Long). */
    @Id
    @Column(name = "ID")
    private Long id;

    /** The title. */
    @Column(name = "TITLE", nullable = false)
    private String title;

    /** The description. */
    @Column(name = "DESCRIPTION")
    private String description;

    /** The difficulty. */
    @Column(name = "DIFFICULTY", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private DifficultyEnum difficulty;

    /** The category. */
    @Column(name = "CATEGORY", length = 20)
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    /** The state. */
    @Column(name = "STATE", nullable = false, length = 20)
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

    /** Tipo de verificacion: I = Image, L = Location, Q = Quiz. */
    @Column(name = "VERIFICATION_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    private ChallengeVerificationType verificationType;

    /**
     * Identificador de la entidad de verificacion (p.ej. 00001, 00002, 00003).
     */
    @Column(name = "VERIFICATION_ID")
    private String verificationId;

    /** Number of quiz questions — calculated in service, not stored in DB. */
    @Transient
    private int questionsCount;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = TsidUtil.generate();
        }
    }
}
