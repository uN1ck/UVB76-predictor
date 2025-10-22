package com.genseck.uvb76.predictor.telegram;

import com.genseck.uvb76.predictor.telegram.properties.TelegramProperties;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {
  private final TelegramBot telegramBot;
  private final TelegramProperties properties;

  @Override
  public void sendPrediction(String prediction, String original) {
    log.info("Message to TG: {}", prediction);
    var msgResponse =
        telegramBot.execute(
            new SendMessage(
                    properties.getChannelId(),
                    "<b>УВБ76:</b>\n"
                        + original
                        + "\n\n<b>Мнение экспертов:</b>\n"
                        + HtmlUtils.htmlEscape(prediction))
                .parseMode(ParseMode.HTML));
    if (!msgResponse.isOk()) {
      log.error("Failed to send message to TG {}, {}", msgResponse, msgResponse.description());
    }
  }
}
