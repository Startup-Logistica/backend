package br.portela.startuplogistica.dtos.user.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserInputDTO {
    @NotBlank
    private String name;

    @NotBlank @Email
    private String email;
}
