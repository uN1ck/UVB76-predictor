package com.genseck.uvb76.predictor.ai.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("uvb76.ai.model")
public class AiModelProperties {

  private float temperature;
  private float topP;
  private int tokens;
}
