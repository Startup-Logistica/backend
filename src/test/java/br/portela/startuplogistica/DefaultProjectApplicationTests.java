package br.portela.startuplogistica;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
@ActiveProfiles("test")
class DefaultProjectApplicationTests {

    // injeta um JavaMailSender vazio para satisfazer o SmtpEmailService
    @MockBean
    private JavaMailSender javaMailSender;

    @Test
    void contextLoads() {
        // agora o Spring sobe o contexto sem reclamar do JavaMailSender
    }
}
