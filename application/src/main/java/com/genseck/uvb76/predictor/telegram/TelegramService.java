package com.genseck.uvb76.predictor.telegram;

public interface TelegramService {

  void sendPrediction(String prediction, String original);
}
