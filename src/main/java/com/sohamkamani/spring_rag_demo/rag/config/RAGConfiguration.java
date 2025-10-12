package com.sohamkamani.spring_rag_demo.rag.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class RAGConfiguration {
    @Value("${document.processing.chunk-size:1000}")
    private int chunkSize;

    @Value("${document.processing.chunk-overlap:200}")
    private int chunkOverlap;

    @Value("classpath:/prompts/rag-prompt.st")
    private Resource ragPromptTemplate;

    @Bean
    @Primary
    public ChatClient chatClient(ChatModel chatModel, VectorStore vectorStore) {
        SearchRequest searchRequest = SearchRequest
                .builder()
                .topK(5)
                .similarityThreshold(0.75)
                .build();
        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        QuestionAnswerAdvisor questionAnswerAdvisor = QuestionAnswerAdvisor
                .builder(vectorStore)
                .searchRequest(searchRequest)
                .promptTemplate(promptTemplate)
                .build();
        return ChatClient.builder(chatModel)
                .defaultAdvisors(questionAnswerAdvisor)
                .build();
    }

    @Bean
    public PdfDocumentReaderConfig pdfDocumentReaderConfig() {
        return PdfDocumentReaderConfig
                .builder()
                .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                        .withLeftAlignment(true)
                        .build())
                .build();
    }

    @Bean
    public TokenTextSplitter tokenTextSplitter() {
        return new TokenTextSplitter(chunkSize, chunkOverlap, 5, 10000, true);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
