package com.ushadevi.llm_gateway.provider;

import com.ushadevi.llm_gateway.dto.ChatRequest;
import com.ushadevi.llm_gateway.dto.ChatResponse;
import org.springframework.stereotype.Component;

@Component
public class MockProvider implements LlmProvider {

    @Override
    public String name() { return "mock"; }

    @Override
    public ChatResponse chat(ChatRequest req) {
        String answer = "Mock reply to: " + req.prompt();
        int in = req.prompt().split("\\s+").length;
        int out = answer.split("\\s+").length;
        return new ChatResponse(answer, name(), in, out);
    }
}