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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({
        SecurityConfig.class,
        JwtTokenService.class,
        ApiExceptionHandler.class
})
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class) // Add Mockito extension
public class AuthControllerTest {

    @Mock // Standard Mockito annotation instead of @MockBean
    private LoginUseCase loginUseCase;

    @Mock
    private RequirePasswordRecoveryUseCase requirePasswordRecoveryUseCase;

    @Mock
    private ValidatePasswordRecoveryCodeUseCase validatePasswordRecoveryCodeUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void login_ShouldReturnToken() throws Exception {
        when(loginUseCase.execute(any())).thenReturn(new LoginOutputDTO("test-token"));

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@test.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("test-token"));
    }
}