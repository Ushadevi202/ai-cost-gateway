package com.ushadevi.llm_gateway.provider;

import tools.jackson.databind.JsonNode;
import com.ushadevi.llm_gateway.dto.ChatRequest;
import com.ushadevi.llm_gateway.dto.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
@ConditionalOnExpression("!'${gemini.api-key:}'.isEmpty()")
public class GeminiProvider implements LlmProvider {

    private final RestClient client;
    private final String defaultModel;

    public GeminiProvider(@Value("${gemini.api-key}") String apiKey,
                          @Value("${gemini.model}") String defaultModel,
                          @Value("${gemini.base-url}") String baseUrl) {
        this.defaultModel = defaultModel;
        this.client = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("x-goog-api-key", apiKey)
                .build();
    }

    @Override
    public String name() { return "gemini"; }

    @Override
    public ChatResponse chat(ChatRequest req) {
        String model = (req.model() == null || req.model().isBlank())
                ? defaultModel : req.model();

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", req.prompt())))
                )
        );

        JsonNode json = client.post()
                .uri("/models/{m}:generateContent", model)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(JsonNode.class);

        String answer = json.path("candidates").path(0)
                .path("content").path("parts").path(0)
                .path("text").asText("");
        int in = json.path("usageMetadata").path("promptTokenCount").asInt();
        int out = json.path("usageMetadata").path("candidatesTokenCount").asInt();

        return new ChatResponse(answer, name(), in, out);
    }
}