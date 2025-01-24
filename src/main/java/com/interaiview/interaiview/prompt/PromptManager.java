package com.interaiview.interaiview.prompt;

import lombok.Getter;

@Getter
public enum PromptManager {
    GENERATE_QUESTIONS("The document-based questions, generate 5 questions: ");

    private final String template;

    PromptManager(String template) {
        this.template = template;
    }

}
