package com.ushadevi.llm_gateway.dto;

public record ChatResponse(
        String answer,
        String provider,
        int inputTokens,
        int outputTokens
) {}