package org.example.flowin2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.mail.host=smtp.gmail.com",
    "spring.mail.port=587",
    "spring.mail.username=test@example.com",
    "spring.mail.password=test",
    "jwt.secret=8d97e945f0a7491a9b8e8a7f8f6e0a9d4b3c2d1e0f7a6b5c4d3e2f1a0b9c8d7e"
})
public class FlowIn2ApplicationTests {

    @TestConfiguration(proxyBeanMethods = false)
    static class TestContainersConfig {
        @Bean
        @ServiceConnection
        PostgreSQLContainer<?> postgresContainer() {
            return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
        }
    }

    @Test
    void contextLoads() {
    }
}