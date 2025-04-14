package com.rq.manager.authusers.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.rq.manager.authusers.enumerations.StatesChallengeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

	@Column(name = "STATE")
	@Enumerated(EnumType.STRING)
	private StatesChallengeEnum state;

    /** The start date. */
    @Column(name = "START_DATE"/*, nullable = false*/)
    private LocalDateTime startDate;

    /** The end date. */
    @Column(name = "END_DATE"/*, nullable = false*/)
    private LocalDateTime endDate;

    /** The points. */
    @Column(name = "POINTS", nullable = false)
    private int points;

}
