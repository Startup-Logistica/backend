package br.portela.startuplogistica.usecases.auth;

import br.portela.startuplogistica.config.annotations.UseCase;
import br.portela.startuplogistica.dtos.auth.input.ValidatePasswordRecoveryCodeInputDTO;
import br.portela.startuplogistica.entities.User;
import br.portela.startuplogistica.errors.ExceptionCode;
import br.portela.startuplogistica.errors.exceptions.BusinessRuleException;
import br.portela.startuplogistica.errors.exceptions.EntityNotFoundException;
import br.portela.startuplogistica.repositories.user.UserRepositoryImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ValidatePasswordRecoveryCodeUseCase {
    private final UserRepositoryImpl repository;

    @Transactional
    public void execute(ValidatePasswordRecoveryCodeInputDTO input){
        var user = repository.findByEmail(input.getEmail()).orElseThrow(() -> new EntityNotFoundException(User.class));

        if (!input.getCode().equals(user.getPasswordRecoveryCode()))
            throw new BusinessRuleException(ExceptionCode.INVALID_PASSWORD_RECOVERY_CODE);
    }
}
