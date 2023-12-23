package ru.simple.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.simple.backend.config.APIConfig;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        APIConfig.loadEnv();
        SpringApplication.run(BackendApplication.class, args);
    }
}
