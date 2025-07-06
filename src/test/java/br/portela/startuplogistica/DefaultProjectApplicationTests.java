package br.portela.startuplogistica;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
// substitui toda configuração de DataSource pelo H2 em memória
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class DefaultProjectApplicationTests {

	@Test
	void contextLoads() {
	}

}
