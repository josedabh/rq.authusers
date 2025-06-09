package com.rq.manager.authusers.bean.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta de usuario a una pregunta")
public class UserAnswerDTO {
    
    @Schema(description = "ID de la pregunta", example = "Q00001-P01")
    private String questionId;
    
    @Schema(description = "ID de la respuesta seleccionada", example = "Q00001-P01-R01")
    private String answerId;
}
