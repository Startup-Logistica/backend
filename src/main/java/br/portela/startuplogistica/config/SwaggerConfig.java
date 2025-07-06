package br.portela.startuplogistica.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${spring.mvc.servlet.path}")
    private String apiBasePath;

    @Value("${springdoc.swagger-ui.title}")
    private String swaggerTitle;

    @Value("${springdoc.swagger-ui.version}")
    private String swaggerVersion;

    @Bean
    public OpenAPI customOpenAPI() {
        var apiInfo = new Info()
                .title(swaggerTitle)
                .version(swaggerVersion);

        var apiBaseServer = new Server()
                .description("Default Server URL")
                .url(apiBasePath);

        return new OpenAPI()
                .info(apiInfo)
                .addServersItem(apiBaseServer);
    }
}
