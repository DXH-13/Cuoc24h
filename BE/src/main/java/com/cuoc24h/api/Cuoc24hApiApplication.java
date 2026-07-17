package com.cuoc24h.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

// We authenticate admins via JWT, so exclude the default in-memory user auto-config
// (otherwise Spring Security logs a generated password and creates an unused user).
@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
@ConfigurationPropertiesScan
public class Cuoc24hApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Cuoc24hApiApplication.class, args);
    }
}
