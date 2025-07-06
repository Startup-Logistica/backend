package br.portela.startuplogistica;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "jwt", scheme = "bearer", bearerFormat = "JWT")
public class DefaultProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DefaultProjectApplication.class, args);

        log.info("""
                 \s
                
                     _____ _             _                 _                 _     _   _          \s
                    / ____| |           | |               | |               (_)   | | (_)         \s
                   | (___ | |_ __ _ _ __| |_ _   _ _ __   | |     ___   __ _ _ ___| |_ _  ___ __ _\s
                    \\___ \\| __/ _` | '__| __| | | | '_ \\  | |    / _ \\ / _` | / __| __| |/ __/ _` |
                    ____) | || (_| | |  | |_| |_| | |_) | | |___| (_) | (_| | \\__ \\ |_| | (_| (_| |
                   |_____/ \\__\\__,_|_|   \\__|\\__,_| .__/  |______\\___/ \\__, |_|___/\\__|_|\\___\\__,_|
                                                  | |                   __/ |                     \s
                                                  |_|                  |___/                      \s
                
                 \s
                  DEFAULT PROJECT :: 0.1
                \s""");
    }
}
