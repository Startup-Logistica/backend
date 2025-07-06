package br.portela.startuplogistica.usecases.user;

import br.portela.startuplogistica.config.annotations.UseCase;
import br.portela.startuplogistica.dtos.user.output.UserDetailedOutputDTO;
import br.portela.startuplogistica.entities.User;
import br.portela.startuplogistica.errors.exceptions.EntityNotFoundException;
import br.portela.startuplogistica.mappers.user.UserMapperImpl;
import br.portela.startuplogistica.repositories.user.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class FindUserByIdUseCase {
    private final UserMapperImpl mapper;
    private final UserRepositoryImpl repository;

    public UserDetailedOutputDTO execute(UUID id) {
        var user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class));
        return mapper.toDetailedOutputDTO(user);
    }

    public UserDetailedOutputDTO execute(UUID id, Boolean allowInactive) {
        if (!allowInactive)
            return execute(id);

        var user = repository.findByIdIncludeInactive(id).orElseThrow(() -> new EntityNotFoundException(User.class));
        return mapper.toDetailedOutputDTO(user);
    }
}
