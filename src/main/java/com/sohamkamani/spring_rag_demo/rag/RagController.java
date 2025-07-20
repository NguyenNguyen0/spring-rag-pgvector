package com.sohamkamani.spring_rag_demo.rag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagController {

  private final RagService ragService;

  public RagController(RagService ragService) {
    this.ragService = ragService;
  }

  @GetMapping("/ai/rag")
  public String generate(
      @RequestParam(value = "message", defaultValue = "What is Spring AI?") String message) {
    return ragService.retrieveAndGenerate(message);
  }
}
