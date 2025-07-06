package br.portela.startuplogistica.security.services;

import br.portela.startuplogistica.repositories.user.UserRepositoryImpl;
import br.portela.startuplogistica.security.dto.UserDetailsDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationService implements UserDetailsService {
    private final UserRepositoryImpl userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username);
        user.orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserDetailsDTO(user.get());
    }
}
