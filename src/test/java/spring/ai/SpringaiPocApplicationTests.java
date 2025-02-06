package spring.ai;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import spring.ai.config.TestContainersConfiguration;

@Import(TestContainersConfiguration.class)
@SpringBootTest
class SpringaiPocApplicationTests {

	@Test
	void contextLoads() {
	}

}
