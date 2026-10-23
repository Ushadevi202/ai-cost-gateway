package com.ushadevi.llm_gateway.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(
        @NotBlank String prompt,
        String model

) {}