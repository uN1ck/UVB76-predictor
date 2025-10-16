package com.genseck.uvb76.predictor.telegram;

import com.genseck.uvb76.predictor.telegram.properties.TelegramProperties;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {
    private final TelegramBot telegramBot;
    private final TelegramProperties properties;

    @Override
    public void sendPrediction(String prediction) {
        var msgResponse = telegramBot.execute(new SendMessage(properties.getChannelId(), prediction));
        if (!msgResponse.isOk()) {
            log.error("Failed to send message to TG {}", msgResponse);
        }
    }
}
