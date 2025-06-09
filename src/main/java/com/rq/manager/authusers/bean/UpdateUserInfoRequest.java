package com.rq.manager.authusers.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class UpdateUserInfoRequest.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserInfoRequest {

    /** The email. */
    @Schema(description = "Nuevo email", example = "mario@example.com")
    private String email;

    /** The name. */
    @Schema(description = "Nuevo nombre", example = "Mario")
    private String name;

    /** The lastname. */
    @Schema(description = "Nuevo apellido", example = "Pérez")
    private String lastname;

    /** The username. */
    @Schema(description = "Nuevo nombre de usuario", example = "mariop12")
    private String username;

    /** The num phone. */
    @Schema(description = "Nuevo número de teléfono", example = "623456789")
    private String numPhone;
}
