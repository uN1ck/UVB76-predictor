package com.genseck.uvb76.predictor.telegram.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("uvb76.telegram")
public class TelegramProperties {
    private String token;
    private String channelId;
}
