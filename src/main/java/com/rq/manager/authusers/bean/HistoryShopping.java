package com.rq.manager.authusers.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Detalle del historial de compra de recompensas con datos esenciales")
public class HistoryShopping {

    @Schema(description = "ID de la transacción", example = "101")
    private Long transactionId;

    // --- Datos del usuario ---
    @Schema(description = "Nombre del usuario", example = "Mario")
    private String userName;

    @Schema(description = "Apellido del usuario", example = "Perez")
    private String userLastname;

    @Schema(description = "Username del usuario", example = "mariop12")
    private String userUsername;  // Nombre de usuario en la app

    @Schema(description = "Puntos del usuario antes de la compra", example = "150")
    private Integer pointsBefore;  // Puntos que tenía antes de la compra

    // --- Detalles de la transacción ---
    @Schema(description = "Fecha y hora de la compra", example = "2025-05-24T14:30:00")
    private String purchaseDate;

    // --- Datos de la recompensa ---
    @Schema(description = "Nombre de la recompensa", example = "Auriculares Bluetooth")
    private String rewardName;

    @Schema(description = "Descripción de la recompensa", example = "Auriculares Bluetooth inalámbricos con cancelación de ruido")
    private String rewardDescription;

    @Schema(description = "Puntos requeridos para canjear la recompensa", example = "100")
    private Integer rewardPoints;  // Coste de la recompensa

    @Schema(description = "Puntos del usuario después de la compra", example = "50")
    private Integer pointsAfter;  // Puntos restantes tras la compra
}
