package br.portela.startuplogistica.usecases.user;

import br.portela.startuplogistica.config.annotations.UseCase;
import br.portela.startuplogistica.dtos.email.input.SendEmailInputDTO;
import br.portela.startuplogistica.dtos.user.input.CreateUserInputDTO;
import br.portela.startuplogistica.entities.User;
import br.portela.startuplogistica.enums.EmailTemplate;
import br.portela.startuplogistica.enums.UserRole;
import br.portela.startuplogistica.errors.exceptions.DuplicatedResourceException;
import br.portela.startuplogistica.mappers.user.UserMapperImpl;
import br.portela.startuplogistica.repositories.user.UserRepositoryImpl;
import br.portela.startuplogistica.services.SmtpEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@UseCase
@Slf4j
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final UserMapperImpl mapper;
    private final UserRepositoryImpl repository;
    private final SmtpEmailService smtpEmailService;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Transactional
    public void execute(CreateUserInputDTO input) {
        validate(input);

        var encryptedPassword = bcryptPasswordEncoder.encode(input.getPassword());
        input.setPassword(encryptedPassword);

        var user = repository.findByEmailIncludeInactive(input.getEmail()).orElse(mapper.toEntity(input));
        var newUser = user.withName(input.getName())
                .withRole(UserRole.GUEST)
                .withDisabledAt(null)
                .withEmailValidatedAt(null);

        log.warn("Creating user with email: {} ", input.getEmail());
        var createdUser = repository.save(newUser);
        sendEmailToValidation(createdUser);
    }

    private void validate(CreateUserInputDTO input) {
        if (repository.findByEmail(input.getEmail()).isPresent())
            throw new DuplicatedResourceException(User.class, List.of("email"));
    }

    private void sendEmailToValidation(User user) {
        Map<String, Object> data = new HashMap<>();
        data.put("link", generateUrlToValidateEmail(user.getId()));
        var template = smtpEmailService.processTemplate(data, EmailTemplate.EMAIL_VALIDATION);

        var emailMessage = new SendEmailInputDTO(List.of(user.getEmail()), "Validação de E-mail", template);
        smtpEmailService.sendEmail(emailMessage);
    }

    private String generateUrlToValidateEmail(UUID userId) {
        var serverBaseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/v1";
        return MessageFormat.format("{0}/user/{1}/validate-email", serverBaseUrl, userId);
    }
}
