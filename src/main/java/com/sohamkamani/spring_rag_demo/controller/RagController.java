package com.sohamkamani.spring_rag_demo.controller;

import com.sohamkamani.spring_rag_demo.service.RagService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/rag")
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping("/sync")
    public String generate(@RequestBody MessageRequest request) {
        return ragService.syncGenerate(request.message());
    }

    @PostMapping(value = "/stream", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> streamChat(@RequestBody String message) {
        return ragService.streamGenerate(message);
    }

    public record MessageRequest(String message) {
    }
}

