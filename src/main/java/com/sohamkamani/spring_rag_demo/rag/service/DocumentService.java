package com.sohamkamani.spring_rag_demo.rag.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DocumentService {
    private final Map<String, Document> store = new HashMap<>();
    private final PgVectorStore vectorStore;
    private final EmbeddingModel embeddingModel;

    DocumentService(PgVectorStore vectorStore, EmbeddingModel embeddingModel) {
        this.vectorStore = vectorStore;
        this.embeddingModel = embeddingModel;
    }

    public List<Document> getAll() {
        return vectorStore.similaritySearch(SearchRequest.builder().query("").topK(1000).build());
    }

    public Document get(String id) {
        Optional<JdbcTemplate> optionalJdbcTemplate = vectorStore.getNativeClient();
        if (optionalJdbcTemplate.isPresent()) {
            JdbcTemplate jdbcTemplate = optionalJdbcTemplate.get();
            String sql = "SELECT id, content, metadata FROM vector_store WHERE id = CAST(? AS UUID);";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                Document doc = new Document(rs.getString("content"));
                doc.getMetadata().put("id", UUID.fromString(rs.getString("id")));
                String metadataJson = rs.getString("metadata");
                if (metadataJson != null && !metadataJson.isEmpty()) {
                    // Use a JSON library like Jackson to parse the metadata
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        Map<String, Object> metadataMap = mapper.readValue(metadataJson, new TypeReference<Map<String, Object>>() {
                        });
                        // Add all parsed metadata to the document
                        doc.getMetadata().putAll(metadataMap);
                    } catch (Exception e) {
                        // Handle JSON parsing exception
                        throw new RuntimeException("Failed to parse metadata JSON: " + metadataJson, e);
                    }
                }
                return doc;
            });
        }
        List<Document> results = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query("")
                        .filterExpression("id == '" + id + "'")
                        .topK(1)
                        .build()
        );

        return results.isEmpty() ? null : results.get(0);
    }

    public Document create(Document doc) {
        if (doc.getMetadata().get("id") == null) {
            doc.getMetadata().put("id", UUID.randomUUID().toString());
        }

        vectorStore.add(Collections.singletonList(doc));
        return doc;
    }

    public Document update(String id, Document doc) {
        doc.getMetadata().put("id", id);

        delete(id);

        vectorStore.add(Collections.singletonList(doc));
        return doc;
    }

    public void delete(String id) {
        vectorStore.delete(Collections.singletonList("id == '" + id + "'"));
    }

    public void addAll(List<Document> docs) {
        for (Document doc : docs) {
            if (doc.getMetadata().get("id") == null) {
                doc.getMetadata().put("id", UUID.randomUUID().toString());
            }
        }
        vectorStore.add(docs);
    }
}