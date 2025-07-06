package br.portela.startuplogistica.usecases.user;

import br.portela.startuplogistica.dtos.user.input.UpdateUserInputDTO;
import br.portela.startuplogistica.repositories.user.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateUserUseCase {
    private final UserRepositoryImpl userRepositoryImpl;

    public void execute(UUID id, UpdateUserInputDTO dto) throws ChangeSetPersister.NotFoundException {
        var user = userRepositoryImpl.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        userRepositoryImpl.save(user);
    }
}

