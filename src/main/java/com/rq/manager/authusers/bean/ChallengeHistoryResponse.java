package com.rq.manager.authusers.bean;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Bean para listar el historial de retos completados por usuario.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Historial de retos completados por el usuario")
public class ChallengeHistoryResponse {

    @Schema(description = "Nombre del usuario", example = "Juan")
    private String userName;

    @Schema(description = "Apellido del usuario", example = "González")
    private String userLastname;

    @Schema(description = "Username del usuario", example = "juan.gonzalez")
    private String userUsername;

    @Schema(description = "Título del reto completado", example = "Maratón de Lectura")
    private String challengeTitle;

    @Schema(description = "Fecha y hora en que se completó el reto", example = "2025-06-08T14:30:00")
    private LocalDateTime completedAt;

    @Schema(description = "Puntos obtenidos al completar el reto", example = "50")
    private int earnedPoints;

    @Schema(description = "Número de intentos realizados antes de completar", example = "1")
    private int attempts;
}
