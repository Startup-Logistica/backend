package br.portela.startuplogistica.rest.controllers;

import br.portela.startuplogistica.DefaultProjectApplication;
import br.portela.startuplogistica.usecases.user.CreateUserUseCase;
import br.portela.startuplogistica.usecases.user.FindUserByIdUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {
                DefaultProjectApplication.class,
                TestSecurityConfig.class,
                UserControllerIntegrationTest.TestConfig.class // Add test config
        }
)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private FindUserByIdUseCase findUserByIdUseCase;

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public CreateUserUseCase createUserUseCase() {
            return mock(CreateUserUseCase.class);
        }

        @Bean
        @Primary
        public FindUserByIdUseCase findUserByIdUseCase() {
            return mock(FindUserByIdUseCase.class);
        }

        // Add similar @Bean methods for other use cases
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_ShouldReturnCreated() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test User\",\"email\":\"new@user.com\",\"password\":\"validPassword123\"}"))
                .andExpect(status().isCreated());
    }
}