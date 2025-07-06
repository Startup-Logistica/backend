package br.portela.startuplogistica.dtos.auth.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.Objects;

@Getter
public class RequirePasswordRecoveryInputDTO {

    @Email
    @NotBlank
    @Schema(example = "example@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @JsonCreator
    public RequirePasswordRecoveryInputDTO(String email) {
        this.email = Objects.isNull(email) ? null : email.trim();
    }
}
