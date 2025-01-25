package com.interaiview.interaiview.ai.prompt;

import lombok.Getter;

@Getter
public enum PromptManager {
    GENERATE_QUESTIONS("You are an interviewer. Based on the resume details, generate 5 interview questions with the answer sections left blank and feedback sections left blank in the format(Do not wrap the json codes in JSON markers): { \"qas\": [{\"question\":, \"answer\":\"\", \"feedback\":\"\"},...]}"),
    GENERATE_RESULT("You are the world's best interview advisor. Based on the given question and the answer, provide feedback for each question and answer. And add an overall feedback at the end. The output format(Do not wrap the json codes in JSON markers and Keep the original questions as they are, and summarize and output the answer content neatly.) is { \"qas\": [{\"question\":, \"answer\":\"\", \"feedback\":\"\"},...], \"result\": \"\"}");

    private final String prompt;

    PromptManager(String prompt) {
        this.prompt = prompt;
    }
}
