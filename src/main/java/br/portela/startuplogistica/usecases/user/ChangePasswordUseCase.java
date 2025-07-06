package br.portela.startuplogistica.usecases.user;

import br.portela.startuplogistica.config.annotations.UseCase;
import br.portela.startuplogistica.dtos.user.input.ChangePasswordInputDTO;
import br.portela.startuplogistica.entities.User;
import br.portela.startuplogistica.errors.ExceptionCode;
import br.portela.startuplogistica.errors.exceptions.BusinessRuleException;
import br.portela.startuplogistica.errors.exceptions.EntityNotFoundException;
import br.portela.startuplogistica.repositories.user.UserRepositoryImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@UseCase
@RequiredArgsConstructor
public class ChangePasswordUseCase {
    private final UserRepositoryImpl repository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Transactional
    public void execute(ChangePasswordInputDTO input) {
        var user = repository.findByEmail(input.getEmail()).orElseThrow(() -> new EntityNotFoundException(User.class));

        validate(user, input);

        user.setPasswordRecoveryCode(null);
        user.setPassword(bcryptPasswordEncoder.encode(input.getPassword()));
        repository.save(user);
    }

    private void validate(User user, ChangePasswordInputDTO input) {
        if (!input.getCode().equals(user.getPasswordRecoveryCode()))
            throw new BusinessRuleException(ExceptionCode.INVALID_PASSWORD_RECOVERY_CODE);
    }
}
