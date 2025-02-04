package spring.ai.functioncalling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class Config {

    @Bean
    @Description("Get the weather in location") // function description
    public Function<MockWeatherService.Request, MockWeatherService.Response> currentWeather() {
        return new MockWeatherService();
    }

    /**
     * another way to define a function
    @Bean
    public FunctionCallback weatherFunctionInfo() {

        return FunctionCallback.builder()
                .function("currentWeather", new MockWeatherService()) // (1) function name and instance
                .description("Get the weather in location") // (2) function description
                .inputType(MockWeatherService.Request.class) // (3) function input type
                .build();
    }**/
}
