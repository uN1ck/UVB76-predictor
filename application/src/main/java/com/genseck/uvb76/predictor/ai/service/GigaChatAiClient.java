package com.genseck.uvb76.predictor.ai.service;

import chat.giga.client.GigaChatClient;
import chat.giga.model.ModelName;
import chat.giga.model.completion.ChatMessage;
import chat.giga.model.completion.CompletionRequest;
import com.genseck.uvb76.predictor.ai.AiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class GigaChatAiClient implements AiClient {
    private final GigaChatClient client;
    private static final String PROMPT = """
            Придумай предсказание нашего будущего основываясь на сообщений Радиостанции УВБ76.
            Это радиостанция, которая транслирует зашифрованные военные сообщения.
            Она работает уже тридцать лет и отправка сообщений это еще не признак чего-то опасного.
            
            Объясни какое будущее нас ждет после сообщения. Отвечай коротко и емко. на русском языке.
            
            """;

    @Override
    public String processMessage(String message) {
        try {
            var result = client.completions(CompletionRequest.builder()
                    .model(ModelName.GIGA_CHAT)
                    .message(ChatMessage.builder()
                            .content(PROMPT + message)
                            .role(ChatMessage.Role.USER)
                            .build())
                    .temperature(1.8f)
                    .topP(0.6f)
                    .build());


            return result.choices().get(0).message().content();
        } catch (Exception e) {
            log.error("Error ", e);
            return null;
        }
    }
}
