package spring.ai.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class TestContainersConfiguration {

    private final static String PG_VECTOR_IMAGE = "pgvector/pgvector:pg16";

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgres() {
        var image = DockerImageName.parse(PG_VECTOR_IMAGE)
                .asCompatibleSubstituteFor("postgres");
        return new PostgreSQLContainer<>(image);
    }
}
