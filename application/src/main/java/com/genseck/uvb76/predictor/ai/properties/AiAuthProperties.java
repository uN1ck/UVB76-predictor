package com.genseck.uvb76.predictor.ai.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("uvb76.ai")
public class AiAuthProperties {
    private String authKey;
}
