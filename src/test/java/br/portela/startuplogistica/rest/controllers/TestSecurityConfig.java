package br.portela.startuplogistica.rest.controllers;

import br.portela.startuplogistica.errors.i18n.MessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.mock;

@Configuration
@Profile("test")
public class TestSecurityConfig {
    @Bean
    @Primary
    public MessageService messageService() {
        return mock(MessageService.class);
    }

    // Add other test-specific beans here
}