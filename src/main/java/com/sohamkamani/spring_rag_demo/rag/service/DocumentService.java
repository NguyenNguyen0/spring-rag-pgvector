package com.sohamkamani.spring_rag_demo.rag.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sohamkamani.spring_rag_demo.util.Util;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DocumentService {
    private final VectorStore vectorStore;
    private final JdbcTemplate jdbcTemplate;
    private final TokenTextSplitter textSplitter;
    private final PdfDocumentReaderConfig pdfDocumentReaderConfig;

    public DocumentService(VectorStore vectorStore, JdbcTemplate jdbcTemplate, TokenTextSplitter textSplitter, PdfDocumentReaderConfig pdfDocumentReaderConfig) {
        this.vectorStore = vectorStore;
        this.jdbcTemplate = jdbcTemplate;
        this.textSplitter = textSplitter;
        this.pdfDocumentReaderConfig = pdfDocumentReaderConfig;
    }

    public List<Document> getAll() {
        return vectorStore.similaritySearch(SearchRequest.builder().query("").topK(1000).build());
    }

    public Document getById(String id) {
        String sql = "SELECT id, content, metadata FROM vector_store WHERE id = CAST(? AS UUID);";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Document doc = new Document(rs.getString("content"));
            doc.getMetadata().put("id", UUID.fromString(rs.getString("id")));
            String metadataJson = rs.getString("metadata");
            if (metadataJson != null && !metadataJson.isEmpty()) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> metadataMap = mapper.readValue(
                            metadataJson,
                            new TypeReference<>() {
                            });
                    doc.getMetadata().putAll(metadataMap);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to parse metadata JSON: " + metadataJson, e);
                }
            }
            return doc;
        });
    }

    public void ingestTextDocument(String text, Map<String, Object> metadata) {
        try {
            Document document = new Document(text, metadata);
            List<Document> chunks = textSplitter.split(List.of(document));
            vectorStore.add(chunks);
        } catch (Exception e) {
            throw new RuntimeException("Failed to ingest text", e);
        }
    }

    public void ingestPdfDocument(Resource pdfResource) {
        try {
            DocumentReader reader = new PagePdfDocumentReader(pdfResource, pdfDocumentReaderConfig);
            List<Document> documents = reader.get()
                    .stream()
                    .map(doc ->
                            new Document(Objects.requireNonNull(Util.sanitizeText(doc.getText())))
                    )
                    .toList();

            List<Document> chunks = textSplitter.split(documents);

            chunks.forEach(doc -> {
                if (doc.getMetadata().get("id") == null) {
                    doc.getMetadata().put("id", UUID.randomUUID().toString());
                }
            });

            vectorStore.add(chunks);
        } catch (Exception e) {
            throw new RuntimeException("Failed to ingest document", e);
        }
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
        vectorStore.delete("id == '" + id + "'");
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