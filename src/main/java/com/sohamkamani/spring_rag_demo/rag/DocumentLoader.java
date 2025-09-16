package com.sohamkamani.spring_rag_demo.rag;

import com.sohamkamani.spring_rag_demo.rag.service.DocumentService;
import org.springframework.ai.document.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentLoader implements CommandLineRunner {

    private final DocumentService documentService;

    public DocumentLoader(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public void run(String... args) {
        List<Document> docs = new ArrayList<>();
        try {
            Path dir = Paths.get("src/main/resources/documents");
            if (Files.exists(dir)) {
                Files.list(dir)
                        .filter(p -> p.toString().endsWith(".md"))
                        .forEach(p -> {
                            try {
                                String content = Files.readString(p);
                                docs.add(new Document(content));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        documentService.addAll(docs);
        System.out.println("Loaded .md documents.");
    }
}
