package spring.ai.chat.model.chatclient.toolcalling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigTools {

    @Bean
    public DateTimeTools dateTimeTools() {
       return new DateTimeTools();
    }
}
