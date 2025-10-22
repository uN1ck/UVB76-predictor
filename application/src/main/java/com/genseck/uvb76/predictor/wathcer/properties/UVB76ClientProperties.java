package com.genseck.uvb76.predictor.wathcer.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("uvb76.client")
public class UVB76ClientProperties {
  private String baseUrl;
  private String channel;
}
