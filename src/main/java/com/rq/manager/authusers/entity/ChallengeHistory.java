package com.rq.manager.authusers.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class ChallengeHistory.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
/** Clase de historial challenge de prueba hasta que despliegue*/
public class ChallengeHistory {
    
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    /** The user challenge. */
    @ManyToOne
    @JoinColumn(name = "user_challenge_id")
    private UserChallenge userChallenge;
    
    /** The timestamp. */
    @Column(name = "TIMESTAMP")
    private LocalDateTime timestamp;
    
    /** The event type. */
    @Column(name = "EVENT_TYPE", nullable = false)
    private String eventType; // Ej: "START", "PROGRESS_UPDATE", "COMPLETION"
    
    /** The interaction details. */
    @Column(columnDefinition = "JSON")
    private String interactionDetails; // Almacena el JSON de la interacción
}
