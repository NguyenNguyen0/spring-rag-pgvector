package com.sohamkamani.spring_rag_demo.rag.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class RagService {
    private final ChatClient chatClient;

    public RagService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String syncGenerate(String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatClient.prompt(prompt).call().content();
    }

    public Flux<String> streamGenerate(String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatClient.prompt(prompt).stream().content();
    }
}
