package com.rq.manager.authusers.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class FormPassword.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormPassword {
    
    /** The old password. */
    @NotBlank
    @Schema(description = "the password", example = "example1234")
    private String oldPassword;
    
    /** The new password. */
    @NotBlank
    @Schema(description = "the new password", example = "newexample1234")
    private String newPassword;
    
    /** The verify new password. */
    @NotBlank
    @Schema(description = "the verify new password", example = "newexample1234")
    private String verifyNewPassword;
}
