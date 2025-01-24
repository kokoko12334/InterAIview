package com.interaiview.interaiview.ai.assistant;

public interface ChatAssistant<T, U> {
    T request(U message);
}
