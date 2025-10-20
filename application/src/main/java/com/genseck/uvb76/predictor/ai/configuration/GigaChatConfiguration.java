package com.genseck.uvb76.predictor.ai.configuration;

import chat.giga.client.GigaChatClient;
import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder;
import chat.giga.model.Scope;
import com.genseck.uvb76.predictor.ai.AiClient;
import com.genseck.uvb76.predictor.ai.properties.AiAuthProperties;
import com.genseck.uvb76.predictor.ai.service.GigaChatAiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AiAuthProperties.class)
@RequiredArgsConstructor
public class GigaChatConfiguration {
    private final AiAuthProperties aiAuthProperties;

    @Bean
    public AiClient newGigaChat() {
        GigaChatClient client = GigaChatClient.builder()
                .authClient(AuthClient.builder()
                        .withOAuth(AuthClientBuilder.OAuthBuilder.builder()
                                .scope(Scope.GIGACHAT_API_PERS)
                                .authKey(aiAuthProperties.getAuthKey())
                                .build())
                        .build())
                .build();
        return new GigaChatAiClient(client);
    }

}
