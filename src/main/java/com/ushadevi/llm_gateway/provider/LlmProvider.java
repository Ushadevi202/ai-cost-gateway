package com.ushadevi.llm_gateway.provider;

import com.ushadevi.llm_gateway.dto.ChatRequest;
import com.ushadevi.llm_gateway.dto.ChatResponse;

public interface LlmProvider {
    String name();
    ChatResponse chat(ChatRequest request);
}