package com.sohamkamani.spring_rag_demo.rag.controller;

// src/main/java/com/sohamkamani/spring_rag_demo/rag/DocumentController.java

import com.sohamkamani.spring_rag_demo.rag.service.DocumentService;
import org.springframework.ai.document.Document;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public List<Document> getAll() {
        return documentService.getAll();
    }

    @GetMapping("/{id}")
    public Document get(@PathVariable String id) {
        Document result = documentService.get(id);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found with id: " + id);
        }
        return result;
    }

    @PostMapping
    public Document create(@RequestBody Document doc) {
        return documentService.create(doc);
    }

    @PutMapping("/{id}")
    public Document update(@PathVariable String id, @RequestBody Document doc) {
        return documentService.update(id, doc);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        documentService.delete(id);
    }
}