package br.portela.startuplogistica.dtos.user.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordInputDTO {

    @Email
    @NotBlank
    @Schema(example = "example@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @JsonCreator
    public ChangePasswordInputDTO(String email, String code, String password) {
        this.email = Objects.isNull(email) ? null : email.trim();
        this.code = code;
        this.password = password;
    }
}
