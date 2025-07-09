package br.portela.startuplogistica.rest.controllers;

import br.portela.startuplogistica.dtos.auth.output.LoginOutputDTO;
import br.portela.startuplogistica.errors.ApiExceptionHandler;
import br.portela.startuplogistica.security.config.SecurityConfig;
import br.portela.startuplogistica.security.services.JwtTokenService;
import br.portela.startuplogistica.usecases.auth.LoginUseCase;
import br.portela.startuplogistica.usecases.auth.RequirePasswordRecoveryUseCase;
import br.portela.startuplogistica.usecases.auth.ValidatePasswordRecoveryCodeUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({
        SecurityConfig.class,
        JwtTokenService.class,
        ApiExceptionHandler.class,
        TestSecurityConfig.class  // Add this
})
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private LoginUseCase loginUseCase;

    @TestConfiguration
    static class MockConfig {
        @Bean
        @Primary
        public LoginUseCase loginUseCase() {
            LoginUseCase mock = mock(LoginUseCase.class);
            when(mock.execute(any())).thenReturn(new LoginOutputDTO("test-token"));
            return mock;
        }
    }

    @Test
    void login_ShouldReturnToken() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@test.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("test-token"));
    }
}