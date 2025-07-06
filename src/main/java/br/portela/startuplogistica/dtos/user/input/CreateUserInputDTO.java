package br.portela.startuplogistica.dtos.user.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserInputDTO {

    @NotBlank
    @Schema(example = "Jorge", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Email
    @NotBlank
    @Schema(example = "jorge@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @JsonCreator
    public CreateUserInputDTO(String name, String email, String password) {
        this.name = Objects.isNull(name) ? null : name.trim();
        this.email = Objects.isNull(email) ? null : email.trim();;
        this.password = password;
    }
}
