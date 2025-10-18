package com.sohamkamani.spring_rag_demo.service;

import com.sohamkamani.spring_rag_demo.config.RAGRoomTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class RagService {
    private final QuestionAnswerAdvisor questionAnswerAdvisor;
    private final ChatModel chatModel;
    private final RAGRoomTools roomTools;

    public String syncGenerate(String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return ChatClient.create(chatModel)
                .prompt(prompt)
                .advisors(questionAnswerAdvisor)
//                .tools(roomTools)
                .call()
                .content();
    }

    public Flux<String> streamGenerate(String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return ChatClient.create(chatModel)
                .prompt(prompt)
                .advisors(questionAnswerAdvisor)
//                .tools(roomTools)
                .stream()
                .content();
    }
}
