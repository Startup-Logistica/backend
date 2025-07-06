package br.portela.startuplogistica.rest.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    private String token;

    @BeforeEach
    void obterToken() throws Exception {
        // Ajuste os valores conforme um usuário existente no seu banco de teste
        String loginJson = """
            {"email":"admin@teste.com","password":"123456"}
        """;

        var res = mvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject obj = new JSONObject(res.getResponse().getContentAsString());
        token = obj.getString("token");
    }

    @Test
    void deveListarUsuariosPaginados() throws Exception {
        mvc.perform(get("/api/v1/users")
                        .param("page", "0")
                        .param("limit", "10")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").isNotEmpty());
    }

    @Test
    void deveObterUsuarioLogado() throws Exception {
        mvc.perform(get("/api/v1/user")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("admin@teste.com"));
    }
}

