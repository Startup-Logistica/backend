package br.portela.startuplogistica.usecases.user;

import br.portela.startuplogistica.config.annotations.UseCase;
import br.portela.startuplogistica.repositories.user.UserRepositoryImpl;
import br.portela.startuplogistica.services.AuthenticationContextServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.time.LocalDateTime;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class DeleteUserInformationUseCase {
    private final UserRepositoryImpl repository;
    private final AuthenticationContextServiceImpl authService;
    private final UserRepositoryImpl userRepositoryImpl;

    public void execute() {
        var user = authService.getLoggedUser().withDisabledAt(LocalDateTime.now());
        repository.save(user);
    }

    public void executeId(UUID userId) throws ChangeSetPersister.NotFoundException {
        var user = userRepositoryImpl.findById(userId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        userRepositoryImpl.delete(user);
    }

}
