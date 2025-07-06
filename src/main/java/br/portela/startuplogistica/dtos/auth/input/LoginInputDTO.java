package br.portela.startuplogistica.dtos.auth.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.Objects;

@Getter
public class LoginInputDTO {

    @Email
    @NotBlank
    @Schema(example = "example@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @JsonCreator
    public LoginInputDTO(String name, String email, String password) {
        this.email = Objects.isNull(email) ? null : email.trim();
        this.password = password;
    }
}
