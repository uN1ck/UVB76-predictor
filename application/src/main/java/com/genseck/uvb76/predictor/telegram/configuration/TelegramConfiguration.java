package com.genseck.uvb76.predictor.telegram.configuration;

import com.genseck.uvb76.predictor.telegram.properties.TelegramProperties;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.GetChat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@RequiredArgsConstructor
@Configuration
@Import(TelegramProperties.class)
public class TelegramConfiguration {

  private final TelegramProperties properties;

  @Bean
  public TelegramBot newTelegramBot() {
    TelegramBot bot = new TelegramBot(properties.getToken());

    var response = bot.execute(new GetChat(properties.getChannelId()));
    log.warn("Checking self-identity {}", response);

    bot.setUpdatesListener(
        updates -> {
          updates.forEach(
              update -> {
                log.warn("UPDATE: {}", update);
              });

          return UpdatesListener.CONFIRMED_UPDATES_ALL;
        },
        e -> {
          if (e.response() != null) {
            // got bad response from telegram
            e.response().errorCode();
            e.response().description();
          } else {
            // probably network error
            e.printStackTrace();
          }
        });

    return bot;
  }
}
