package com.ushadevi.llm_gateway.service;

import com.ushadevi.llm_gateway.dto.ChatRequest;
import com.ushadevi.llm_gateway.dto.ChatResponse;
import com.ushadevi.llm_gateway.provider.LlmProvider;
import org.springframework.stereotype.Service;

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
        return providers.get("mock").chat(req);
    }
}