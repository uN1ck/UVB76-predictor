package com.genseck.uvb76.predictor.ai.configuration;

import chat.giga.client.GigaChatClientAsync;
import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder;
import chat.giga.model.Scope;
import com.genseck.uvb76.predictor.ai.AiClient;
import com.genseck.uvb76.predictor.ai.properties.AiAuthProperties;
import com.genseck.uvb76.predictor.ai.properties.AiModelProperties;
import com.genseck.uvb76.predictor.ai.service.GigaChatAiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@RequiredArgsConstructor
@Import({AiAuthProperties.class, AiModelProperties.class})
public class GigaChatConfiguration {
    private final AiAuthProperties aiAuthProperties;

    @Bean
    public AiClient newGigaChat(AiModelProperties aiModelProperties) {

        GigaChatClientAsync client = GigaChatClientAsync.builder()
                .verifySslCerts(false)
                .authClient(AuthClient.builder()
                        .withOAuth(AuthClientBuilder.OAuthBuilder.builder()
                                .scope(Scope.GIGACHAT_API_PERS)
                                .authKey(aiAuthProperties.getAuthKey())
                                .build())
                        .build())
                .connectTimeout(24000)
                .logRequests(true)
                .logResponses(true)
                .build();

        return new GigaChatAiClient(client, aiModelProperties);
    }

}
