package spring.ai.chat.model.chatclient.toolcalling;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TooCallController {

    private final ChatClient chatClient;

    public TooCallController(ChatClient.Builder chatClientBuilder, DateTimeTools dateTimeTools) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultTools(dateTimeTools)
                .build();
    }

    @GetMapping(path = "/time")
    public String getCurrentDateTime(){
        return chatClient.prompt("What day is tomorrow ?")
                .call()
                .content();
    }

    @GetMapping(path = "/alarm")
    public String congifureAlarm(){
        return chatClient.prompt("Can you set an alarm 10 minutes from now?")
                .call()
                .content();
    }
}
