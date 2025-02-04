package spring.ai.chatclient;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
public class ChatClientController {

    private final ChatClient chatClient;

    public ChatClientController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @GetMapping(value = "/joke", produces = "text/plain")
    String joke() {
        var userInput = "Tell me a joke";

        return chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }

    @GetMapping(value = "/async/joke", produces = "text/plain")
    Flux<String> asyncJoke() {
        var userInput = "Tell me a joke";

        var prompt = new Prompt(userInput, OpenAiChatOptions.builder()
                .model(OpenAiApi.ChatModel.GPT_4_TURBO)
                .temperature(0.4)
                .build());

        return chatClient.prompt(prompt)
                .stream()
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
