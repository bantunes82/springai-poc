package spring.ai.chatclient;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MyController {

    private ChatClient chatClient;

    public MyController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping(value = "/joke", produces = "text/plain")
    String joke() {
        var userInput = "Tell me a joke";

        return chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }

    @GetMapping(value = "/soccer/{team}", produces = "application/json")
    SoccerTeam team(@PathVariable String team) {
        var pt = new PromptTemplate("""
                Tell the history of the soccer {team}.
                Please provide the name of the team, the country, the stadium, the coach, the foundation year and a list of historical events.
                Please be short and provide only the most important information.
                Don't send more than 3 phrases.
                """);

        Prompt prompt = pt.create(Map.of("team", team));

        return chatClient.prompt(prompt)
                .call()
                .entity(SoccerTeam.class);
    }

}
