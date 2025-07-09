package br.portela.startuplogistica.rest.controllers;

import br.portela.startuplogistica.errors.ExceptionCode;
import br.portela.startuplogistica.errors.i18n.MessageService;
import br.portela.startuplogistica.security.services.JwtTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
@Profile("test")
public class TestSecurityConfig {

    @Bean
    @Primary
    public MessageService messageService() {
        MessageService mock = mock(MessageService.class);
        when(mock.get(any(ExceptionCode.class)))
                .thenReturn("Mocked message");
        when(mock.get(any(ExceptionCode.class), any(String[].class)))
                .thenReturn("Mocked message with args");
        return mock;
    }

    @Bean
    @Primary
    public JwtTokenService jwtTokenService() {
        JwtTokenService mock = mock(JwtTokenService.class);
        when(mock.generateToken(any())).thenReturn("mocked-jwt-token");
        return mock;
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}