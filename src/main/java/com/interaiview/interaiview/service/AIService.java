package com.interaiview.interaiview.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interaiview.interaiview.ai.openai.OpenAIChatClient;
import com.interaiview.interaiview.dto.QuestionDTO;
import com.interaiview.interaiview.ai.prompt.PromptManager;
import com.interaiview.interaiview.util.FileProcessor;
import com.interaiview.interaiview.util.FileProcessorFactory;
import com.openai.models.ChatCompletion;
import com.openai.models.ChatCompletionCreateParams;
import com.openai.models.ChatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OpenAIChatClient client;
    private final FileProcessorFactory fileProcessorFactory;

    public QuestionDTO generateQuestion(File file, String fileType) throws IOException {
        FileProcessor fileProcessor = fileProcessorFactory.getFileProcessor(fileType);
        PromptManager promptManager = PromptManager.GENERATE_QUESTIONS;

        String userMessage = fileProcessor.extractText(file);
        String systemMessage = promptManager.getPrompt();
        String assistantMessage = "";

        ChatCompletionCreateParams message = client.createParams(userMessage, systemMessage, assistantMessage, ChatModel.GPT_4O_MINI);
        ChatCompletion response = client.request(message);

        Optional<String> content = response.choices()
                .get(0)
                .message()
                .content();
        String jsonData = content.orElseThrow(() -> new RuntimeException("Content is missing in the response"));
        try {
            return objectMapper.readValue(jsonData, QuestionDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to process the JSON data", e);
        }
    }

//    public ChatCompletion generateInterviewResults
}
