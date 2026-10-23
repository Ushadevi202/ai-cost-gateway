package com.ushadevi.llm_gateway.service;

import com.ushadevi.llm_gateway.dto.ChatRequest;
import com.ushadevi.llm_gateway.dto.ChatResponse;
import com.ushadevi.llm_gateway.provider.LlmProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final Map<String, LlmProvider> providers;

    public ChatService(List<LlmProvider> list) {
        this.providers = list.stream()
                .collect(Collectors.toMap(LlmProvider::name, p -> p));
    }

    public ChatResponse chat(ChatRequest req) {
        String name = (req.provider() == null || req.provider().isBlank())
                ? "mock" : req.provider();

        LlmProvider provider = providers.get(name);
        if (provider == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Unknown provider: " + name + ". Available: " + providers.keySet());
        }
        return provider.chat(req);
    }
}