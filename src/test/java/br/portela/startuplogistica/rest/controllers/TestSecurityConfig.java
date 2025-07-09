package br.portela.startuplogistica.rest.controllers;

import br.portela.startuplogistica.errors.ExceptionCode;
import br.portela.startuplogistica.errors.i18n.MessageService;
import br.portela.startuplogistica.security.services.JwtTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@Configuration
@Profile("test")
public class TestSecurityConfig {

    /**
     * Mock for MessageService with predefined responses.
     */
    @Bean
    @Primary
    public MessageService messageService() {
        MessageService mock = mock(MessageService.class);
        when(mock.get(ExceptionCode.valueOf(anyString()))).thenReturn("Mocked message");
        return mock;
    }

    /**
     * Mock for JWT Token Service (commonly used in security tests).
     */
    @Bean
    @Primary
    public JwtTokenService jwtTokenService() {
        JwtTokenService mock = mock(JwtTokenService.class);
        when(mock.generateToken(any())).thenReturn("mocked-jwt-token");
        return mock;
    }

    /**
     * Mock for PasswordEncoder (security requirement).
     */
    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder mock = mock(PasswordEncoder.class);
        when(mock.encode(anyString())).thenReturn("encoded-password");
        when(mock.matches(anyString(), anyString())).thenReturn(true);
        return mock;
    }

    // Add more commonly used mocks here (e.g., repositories, clients, etc.)
}