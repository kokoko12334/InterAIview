package com.interaiview.interaiview.ai.prompt;

import lombok.Getter;

@Getter
public enum PromptManager {
    GENERATE_QUESTIONS("You are an interviewer. Based on the resume details, generate 5 interview questions in the format(Do not wrap the json codes in JSON markers): { \"questions\": [Quetion, Quetion, ...]}");

    private final String prompt;

    PromptManager(String prompt) {
        this.prompt = prompt;
    }
}
