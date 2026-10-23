package com.ushadevi.llm_gateway.controller;

import com.ushadevi.llm_gateway.dto.ChatRequest;
import com.ushadevi.llm_gateway.dto.ChatResponse;
import com.ushadevi.llm_gateway.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService service;

    public ChatController(ChatService service) { this.service = service; }

    @PostMapping
    public ChatResponse chat(@Valid @RequestBody ChatRequest req) {
        return service.chat(req);
    }
}