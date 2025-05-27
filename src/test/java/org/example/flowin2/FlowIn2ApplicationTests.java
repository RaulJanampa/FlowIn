package org.example.flowin2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

import org.testcontainers.utility.DockerImageName;

@SpringBootTest
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