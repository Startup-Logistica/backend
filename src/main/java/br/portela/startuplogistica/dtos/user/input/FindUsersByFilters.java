package br.portela.startuplogistica.dtos.user.input;

import br.portela.startuplogistica.dtos.commons.pagination.input.PaginationInputDTO;
import br.portela.startuplogistica.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindUsersByFilters extends PaginationInputDTO {
    private String name;
    private String email;
    private UserRole role;
    private Boolean active;
}
