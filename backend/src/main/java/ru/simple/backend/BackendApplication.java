package ru.simple.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.simple.backend.config.EnvLoader;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        EnvLoader.loadEnv();
        SpringApplication.run(BackendApplication.class, args);
    }
}
