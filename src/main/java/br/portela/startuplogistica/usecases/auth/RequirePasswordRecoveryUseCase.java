package br.portela.startuplogistica.usecases.auth;

import br.portela.startuplogistica.config.annotations.UseCase;
import br.portela.startuplogistica.dtos.auth.input.RequirePasswordRecoveryInputDTO;
import br.portela.startuplogistica.dtos.email.input.SendEmailInputDTO;
import br.portela.startuplogistica.entities.User;
import br.portela.startuplogistica.enums.EmailTemplate;
import br.portela.startuplogistica.errors.exceptions.EntityNotFoundException;
import br.portela.startuplogistica.repositories.user.UserRepositoryImpl;
import br.portela.startuplogistica.services.RandomCodeService;
import br.portela.startuplogistica.services.SmtpEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UseCase
@Slf4j
@RequiredArgsConstructor
public class RequirePasswordRecoveryUseCase {
    private final UserRepositoryImpl repository;
    private final SmtpEmailService smtpEmailService;
    private final RandomCodeService randomCodeService;

    public void execute(RequirePasswordRecoveryInputDTO input){
        var user = repository.findByEmail(input.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        var code = randomCodeService.generate();
        updateUser(user, code);
        sendEmail(code, user.getEmail());
    }

    private void updateUser(User user, String code){
        user.setPasswordRecoveryCode(code);
        repository.save(user);
    }

    private void sendEmail(String newPassword, String receiver){
        Map<String, Object> data = new HashMap<>();
        data.put("code", newPassword);

        var template = smtpEmailService.processTemplate(data, EmailTemplate.FORGOT_PASSWORD);
        log.info("Generating template: {}", template);

        var emailMessage =
                new SendEmailInputDTO(
                        List.of(receiver),
                        "Alteração de senha",
                        template
                );

        smtpEmailService.sendEmail(emailMessage);
    }
}
