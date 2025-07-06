package br.portela.startuplogistica.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    @Schema(description = "Problem identifier")
    private String code;

    @Schema(description = "Message to explain the problem")
    private String reason;

    @Schema(description = "Custom problem details")
    private Object details = null;
}
