package com.genseck.uvb76.predictor.ai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AiClientImpl implements AiClient {
    @Override
    public String processMessage(String message) {
        return "";
    }
}
