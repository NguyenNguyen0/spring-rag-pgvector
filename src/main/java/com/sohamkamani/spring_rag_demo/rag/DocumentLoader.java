package com.sohamkamani.spring_rag_demo.rag;

import java.util.List;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DocumentLoader implements CommandLineRunner {

  private final VectorStore vectorStore;

  public DocumentLoader(VectorStore vectorStore) {
    this.vectorStore = vectorStore;
  }

  @Override
  public void run(String... args) throws Exception {
    List<Document> documents = List.of(
        new Document(
            "Spring AI provides a comprehensive framework for developing AI applications."),
        new Document("PGVector is a PostgreSQL extension for vector similarity search."),
        new Document(
            "Retrieval Augmented Generation (RAG) combines retrieval of relevant information with text generation."),
        new Document(
            "Spring Boot simplifies the development of production-ready Spring applications."));
    vectorStore.add(documents);
    System.out.println("Documents loaded into VectorStore.");
  }
}
