package com.rq.manager.authusers.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Email
    @Size(max = 100)
    @Schema(description = "Nuevo email", example = "mario@example.com")
    private String email;

    /** The name. */
    @Size(max = 80)
    @Schema(description = "Nuevo nombre", example = "Mario")
    private String name;

    /** The lastname. */
    @Size(max = 150)
    @Schema(description = "Nuevo apellido", example = "Pérez")
    private String lastname;

    /** The username. */
    @Size(max = 100)
    @Schema(description = "Nuevo nombre de usuario", example = "mariop12")
    private String username;

    /** The num phone. */
    @Size(min = 9, max = 9)
    @Pattern(regexp = "\\d{9}")
    @Schema(description = "Nuevo número de teléfono", example = "623456789")
    private String numPhone;
}
