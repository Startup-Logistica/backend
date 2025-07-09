package br.portela.startuplogistica.rest.controllers;

import br.portela.startuplogistica.DefaultProjectApplication;
import br.portela.startuplogistica.usecases.user.CreateUserUseCase;
import br.portela.startuplogistica.usecases.user.FindUserByIdUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {
                DefaultProjectApplication.class,
                TestSecurityConfig.class
        }
)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @MockBean
    private CreateUserUseCase createUserUseCase;

    @MockBean
    private FindUserByIdUseCase findUserByIdUseCase;

    // Mock all other use cases used by UserController

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_ShouldReturnCreated() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test User\",\"email\":\"new@user.com\",\"password\":\"validPassword123\"}"))
                .andExpect(status().isCreated());
    }
}