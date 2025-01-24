package com.interaiview.service;

import com.interaiview.interaiview.ai.openai.OpenAIChatClient;
import com.interaiview.interaiview.dto.QuestionDTO;
import com.interaiview.interaiview.service.AIService;
import com.interaiview.interaiview.util.FileProcessorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AIService.class, FileProcessorFactory.class, OpenAIChatClient.class})
public class AIServiceTest {

    private final AIService aiService;

    @Autowired
    public AIServiceTest(AIService aiService) {
        this.aiService = aiService;
    }

    @Test
    public void generateQuestionTest() throws IOException {
        File file = new ClassPathResource("testdata/word_sample.docx").getFile();

        QuestionDTO questions = aiService.generateQuestion(file, "word");

        System.out.println(questions.getQuestions());
        Assertions.assertThat(questions.getQuestions().size()).isEqualTo(5);
    }
}
