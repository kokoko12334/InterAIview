package com.interaiview.interaiview.ai.openai;

import com.interaiview.interaiview.ai.assistant.ChatAssistant;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.*;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class OpenAIChatClient implements ChatAssistant<ChatCompletion, ChatCompletionCreateParams> {

    private final OpenAIClient client = OpenAIOkHttpClient.builder()
            .fromEnv()
            .maxRetries(3)
            .timeout(Duration.ofSeconds(20L))
            .build();

    @Override
    public ChatCompletion request(ChatCompletionCreateParams message) {
        return client.chat().completions().create(message);
    }

    public ChatCompletionCreateParams createParams(String userMessage, String systemMessage, String assistantMessage, ChatModel model) {
        return ChatCompletionCreateParams.builder()
                .addMessage(createUserMessage(userMessage))
                .addMessage(createSystemMessage(systemMessage))
                .addMessage(createAssistantMessage(assistantMessage))
                .model(model)
                .build();
    }

    private ChatCompletionUserMessageParam createUserMessage(String message) {
        return ChatCompletionUserMessageParam.builder()
                .content(message)
                .build();
    }

    private ChatCompletionSystemMessageParam createSystemMessage(String message) {
        return ChatCompletionSystemMessageParam.builder()
                .content(message)
                .build();
    }

    private ChatCompletionAssistantMessageParam createAssistantMessage(String message) {
        return ChatCompletionAssistantMessageParam.builder()
                .content(message)
                .build();
    }
}
