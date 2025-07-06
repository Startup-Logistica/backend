package br.portela.startuplogistica.usecases.user;

import br.portela.startuplogistica.config.annotations.UseCase;
import br.portela.startuplogistica.dtos.commons.pagination.output.PaginationOutputDTO;
import br.portela.startuplogistica.dtos.user.input.FindUsersByFilters;
import br.portela.startuplogistica.dtos.user.output.UserMinimalOutputDTO;
import br.portela.startuplogistica.mappers.user.UserMapperImpl;
import br.portela.startuplogistica.repositories.user.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class FindAllUsersUseCase {
    private final UserMapperImpl mapper;
    private final UserRepositoryImpl repository;

    public PaginationOutputDTO<UserMinimalOutputDTO> execute(FindUsersByFilters input) {
        return mapper.toMinimalOutputDTO(repository.findAll(input));
    }
}
