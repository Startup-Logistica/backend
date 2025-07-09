package br.portela.startuplogistica.rest.controllers;

import br.portela.startuplogistica.dtos.auth.output.LoginOutputDTO;
import br.portela.startuplogistica.security.config.SecurityConfig;
import br.portela.startuplogistica.security.services.JwtTokenService;
import br.portela.startuplogistica.usecases.auth.LoginUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({SecurityConfig.class, JwtTokenService.class})
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = {
        "custom.jwt.secret=testSecretKey",
        "custom.jwt.expiration=3000"
})
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginUseCase loginUseCase;

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

