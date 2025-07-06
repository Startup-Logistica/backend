package br.portela.startuplogistica.services;

import br.portela.startuplogistica.entities.User;
import br.portela.startuplogistica.errors.exceptions.EntityNotFoundException;
import br.portela.startuplogistica.errors.exceptions.ForbiddenException;
import br.portela.startuplogistica.security.dto.UserDetailsDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationContextServiceImpl {

    public UserDetailsDTO getData() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Optional)
            return this.handleOptionalData((Optional<UserDetailsDTO>) principal);

        if (principal instanceof UserDetailsDTO)
            return (UserDetailsDTO) principal;

        throw new ForbiddenException();
    }

    private UserDetailsDTO handleOptionalData(Optional<UserDetailsDTO> principal) {
        principal.orElseThrow(() -> new EntityNotFoundException(UserDetailsDTO.class));
        return principal.get();
    }

    public User getLoggedUser() {
        return this.getData().getUser();
    }
}
