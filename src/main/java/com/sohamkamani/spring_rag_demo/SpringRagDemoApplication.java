package com.sohamkamani.spring_rag_demo;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringRagDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRagDemoApplication.class, args);
	}

	@Bean
	public EmbeddingModel embeddingModel(@Value("${spring.ai.openai.api-key}") String openaiApiKey,
			@Value("${spring.ai.openai.base-url}") String openaiBaseUrl) {
		// Use the builder pattern for OpenAiEmbeddingModel
		OpenAiApi openAiApi = OpenAiApi.builder().apiKey(openaiApiKey)
				.baseUrl(openaiBaseUrl)
				.build();
		return new OpenAiEmbeddingModel(openAiApi);
	}

}
