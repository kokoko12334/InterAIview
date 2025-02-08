package com.interaiview.interaiview.ai.prompt;

import lombok.Getter;

@Getter
public enum PromptManager {
    GENERATE_QUESTIONS("You are an interviewer. Based on the resume details, generate 10 interview questions and include the intent of each question in the \"tip\" section with a single sentence. with the answer sections left blank and feedback sections left blank in the format(Do not wrap the json codes in JSON markers): { \"qas\": [{\"question\":, \"tip\":, \"answer\":\"\", \"feedback\":\"\"},...]}"),
    GENERATE_RESULT("You are the world's best interview advisor. Based on the given question and the answer, provide feedback for each question and answer. If the answer is empty, mark the feedback as \"empty\" And add an overall feedback at the end.(Keep the original questions, answers as they are) The output format(Do not wrap the json codes in JSON markers) is { \"qas\": [{\"question\":, \"tip\":, \"answer\":\"\", \"feedback\":\"\"},...], \"result\": \"\"}");

    private final String prompt;

    PromptManager(String prompt) {
        this.prompt = prompt;
    }
}
