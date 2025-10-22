package com.genseck.uvb76.predictor.ai.service;

import com.genseck.uvb76.predictor.ai.AiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnMissingBean
public class AiClientImpl implements AiClient {
  @Override
  public String processMessage(String message) {
    return " TEST MODE! " + message;
  }
}
