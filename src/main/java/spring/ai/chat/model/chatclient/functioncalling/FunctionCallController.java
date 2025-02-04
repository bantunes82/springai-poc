package spring.ai.chat.model.chatclient.functioncalling;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@RestController
public class FunctionCallController {

    private final ChatClient chatClient;

    public FunctionCallController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultFunctions("currentWeather")
                .build();
    }

    @GetMapping(value = "/weather", produces = "text/plain")
    String getWeather(@RequestParam(defaultValue = "What's the weather like in San Francisco, Paris and Sao Paulo ?") String location){

        return chatClient.prompt(location)
                .call()
                .content();

    }

    @GetMapping(value = "/weather/toolContext", produces = "text/plain")
    String getWeatherToolContext(@RequestParam(value = "message", defaultValue = "What's the weather like in San Francisco, Tokyo, and Paris?") String message){
        BiFunction<MockWeatherService.Request, ToolContext, MockWeatherService.Response> weatherFunction =
                (request, toolContext) -> {
                    String sessionId = (String) toolContext.getContext().get("sessionId");
                    String userId = (String) toolContext.getContext().get("userId");

                    // Use sessionId and userId in your function logic
                    double temperature = 0;
                    if (request.location().contains("Paris")) {
                        temperature = 15;
                    }
                    else if (request.location().contains("Tokyo")) {
                        temperature = 10;
                    }
                    else if (request.location().contains("San Francisco")) {
                        temperature = 30;
                    }

                    return new MockWeatherService.Response(temperature, MockWeatherService.Unit.C);
                };

        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model(OpenAiApi.ChatModel.GPT_4_O.getValue())
                .functionCallbacks(List.of(FunctionCallback.builder()
                        .function("getCurrentWeather", weatherFunction)
                        .description("Get the weather in location")
                        .inputType(MockWeatherService.Request.class)
                        .build()))
                .toolContext(Map.of("sessionId", "123", "userId", "user456"))
                .build();

        var prompt = new Prompt(message, options);

        return chatClient.prompt(prompt)
                .call()
                .content();

    }


}
