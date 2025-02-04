package spring.ai.functioncalling;

import java.util.function.Function;

public class MockWeatherService implements Function<MockWeatherService.Request, MockWeatherService.Response> {

    public enum Unit { C, F }

    //@JsonClassDescription("Get the weather in location") // // function description (another way)
    public record Request(String location, Unit unit) {}

    public record Response(double temp, Unit unit) {}

    public Response apply(Request request) {
        return new Response(30.0, Unit.C);
    }
}