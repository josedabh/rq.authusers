package com.rq.manager.authusers.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Relación al usuario que hizo la compra.
     * Permite navegar a todos sus campos (nombre, puntos, etc.).
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Relación a la recompensa comprada.
     * Permite acceder a su nombre, descripción y puntos requeridos.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reward_id", nullable = false)
    private Reward reward;

    /**
     * Puntos que el usuario gastó en esta compra (normalmente = reward.getPoints()).
     */
    @Column(name = "points_spent", nullable = false)
    private Integer pointsSpent;

    /**
     * Fecha y hora exacta de la compra.
     * Puedes usar @CreationTimestamp si quieres que Hibernate lo pueble automáticamente.
     */
    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime purchaseDate;
}
