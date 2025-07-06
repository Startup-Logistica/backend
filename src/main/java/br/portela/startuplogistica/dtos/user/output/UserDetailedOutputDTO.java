package br.portela.startuplogistica.dtos.user.output;

import br.portela.startuplogistica.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailedOutputDTO {
    private UUID id;

    @Schema(example = "Jorge")
    private String name;

    @Schema(example = "jorge@gmail")
    private String email;

    private UserRole role;

    private LocalDateTime createdAt;

    @Schema(example = "null")
    private LocalDateTime disabledAt;

    private LocalDateTime emailValidatedAt;
}
