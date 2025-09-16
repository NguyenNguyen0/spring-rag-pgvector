package com.sohamkamani.spring_rag_demo.rag.controller;

import com.sohamkamani.spring_rag_demo.rag.service.RagService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping("/rag")
    public String generate(@RequestBody MessageRequest request) {
        return ragService.retrieveAndGenerate(request.message());
    }

    public record MessageRequest(String message) {
    }
}
