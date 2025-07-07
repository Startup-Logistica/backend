@SpringBootTest(properties = {
    "custom.jwt.expiration=3600",
    "spring.mail.enabled=false"
})
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private JavaMailSender javaMailSender;

    // outros @MockBean (JwtTokenService, SmtpEmailService, etc.)

    @Autowired
    private MockMvc mvc;

    @Test
    void deveListarUsuariosPaginados() throws Exception {
       // ...
    }

    @Test
    void deveObterUsuarioLogado() throws Exception {
       // ...
    }
}
