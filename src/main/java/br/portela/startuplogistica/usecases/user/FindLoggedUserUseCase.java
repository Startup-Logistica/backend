package br.portela.startuplogistica.usecases.user;

import br.portela.startuplogistica.config.annotations.UseCase;
import br.portela.startuplogistica.dtos.user.output.UserDetailedOutputDTO;
import br.portela.startuplogistica.mappers.user.UserMapperImpl;
import br.portela.startuplogistica.services.AuthenticationContextServiceImpl;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class FindLoggedUserUseCase {
    private final UserMapperImpl mapper;
    private final AuthenticationContextServiceImpl authService;

    public UserDetailedOutputDTO execute() {
        return mapper.toDetailedOutputDTO(authService.getLoggedUser());
    }
}
