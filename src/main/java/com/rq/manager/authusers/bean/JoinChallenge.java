package com.rq.manager.authusers.bean;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinChallenge {
    @Schema(description = "User ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID userId;

    @Schema(description = "Challenge ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID challengeId;

    @Schema(description = "Join date", example = "2024-03-20T10:00:00")
    private String joinDate;

    @Schema(description = "Earned points", example = "0")
    @Column(name = "earned_points")
    private int earnedPoints;
}
