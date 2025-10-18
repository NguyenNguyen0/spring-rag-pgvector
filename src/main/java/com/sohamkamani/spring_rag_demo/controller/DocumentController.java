package com.sohamkamani.spring_rag_demo.controller;

import com.sohamkamani.spring_rag_demo.service.DocumentService;
import org.springframework.ai.document.Document;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

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
        Document result = documentService.getById(id);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found with id: " + id);
        }
        return result;
    }

    @PostMapping(value = "/ingest/pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> ingestPdf(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "No PDF file provided!");
        }

        if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Only PDF files are supported");
        }

        Path tempFile = Files.createTempFile("upload-", ".pdf");
        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
        Resource resource = new FileSystemResource(tempFile.toFile());

        documentService.ingestPdfDocument(resource);

        Files.deleteIfExists(tempFile);

        return ResponseEntity.ok(Map.of(
                "message", "PDF document ingested successfully",
                "filename", file.getOriginalFilename()
        ));
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