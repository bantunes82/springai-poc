package spring.ai;

import org.springframework.boot.SpringApplication;
import spring.ai.config.TestContainersConfiguration;

public class TestSpringaiPocApplication {

    public static void main(String[] args) {
        SpringApplication.from(SpringaiPocApplication::main)
                .with(TestContainersConfiguration.class)
                .run(args);
    }
}
