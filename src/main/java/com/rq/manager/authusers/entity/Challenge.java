package com.rq.manager.authusers.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @GeneratedValue(generator = "UUID")
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

    /** The start date. */
    @Column(name = "START_DATE"/*, nullable = false*/)
    private LocalDateTime startDate;

    /** The end date. */
    @Column(name = "END_DATE"/*, nullable = false*/)
    private LocalDateTime endDate;

    /** The points. */
    @Column(name = "POINTS", nullable = false)
    private int points;

    /**
     * Verifica si un usuario puede ver el reto antes de la fecha de inicio.
     * - Si es un evento instantáneo, solo se puede ver a partir de `startDate`.
     * - Si se permite verlo una semana antes, se puede acceder desde `startDate - 7 días`.
     *
     * @param currentDate the current date
     * @param isInstantEvent the is instant event
     * @return true, if successful
     */
    public boolean canUserSeeChallenge(LocalDateTime currentDate, boolean isInstantEvent) {
        if (isInstantEvent) {
            return !currentDate.isBefore(startDate);
        } else {
            return !currentDate.isBefore(startDate.minusDays(7));
        }
    }
}
