package com.genseck.uvb76.predictor.ai.service;

import chat.giga.client.GigaChatClientAsync;
import chat.giga.model.ModelName;
import chat.giga.model.completion.ChatMessage;
import chat.giga.model.completion.ChatMessageRole;
import chat.giga.model.completion.CompletionRequest;
import com.genseck.uvb76.predictor.ai.AiClient;
import com.genseck.uvb76.predictor.ai.properties.AiModelProperties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class GigaChatAiClient implements AiClient {
  private static final String PROMPT =
      """
            Ты аналитик, хорошо знакомый с положением дел в современном мире.
            Придумай предсказание нашего будущего основываясь на сообщений Радиостанции УВБ76.
            Это радиостанция, которая транслирует зашифрованные военные сообщения.
            Она работает уже тридцать лет и отправка сообщений это еще не признак чего-то опасного.
            Интерпретируй смысл сообщения переданного УВБ76. Отвечай коротко и емко. на русском языке.

            """;
  private final GigaChatClientAsync client;
  private final AiModelProperties aiModelProperties;

  @Override
  public String processMessage(String message) {
    try {
      var cf = new CompletableFuture<String>();

      client.completions(
          CompletionRequest.builder()
              .model(ModelName.GIGA_CHAT)
              .message(
                  ChatMessage.builder()
                      .content(PROMPT + message)
                      .role(ChatMessageRole.USER)
                      .build())
              .temperature(aiModelProperties.getTemperature())
              .topP(aiModelProperties.getTopP())
              .maxTokens(Math.min(aiModelProperties.getTokens(), 3000))
              .build(),
          new AiResponseHandler(cf::complete));

      // Ждем пока накопится все сообщение от гигачата
      return cf.get(30, TimeUnit.SECONDS);
    } catch (Exception e) {
      log.error("Error ", e);
      return null;
    }
  }
}
