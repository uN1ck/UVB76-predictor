package com.genseck.uvb76.predictor.service;

import com.genseck.uvb76.predictor.ai.AiClient;
import com.genseck.uvb76.predictor.telegram.TelegramService;
import com.genseck.uvb76.predictor.wathcer.UVBReaderService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewEventsCheckerService {

    private long lastMessageId = -1;

    private final UVBReaderService readerService;
    private final AiClient aiClient;
    private final TelegramService telegramService;

    private final ScheduledExecutorService sec = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    void init() {
        sec.schedule(this::check, 1, TimeUnit.SECONDS);
    }

    private void check() {
        var posts = readerService.getLatestPosts();
        var lastMessage = posts.getMessages().get(0);
        if (lastMessage.getId() == lastMessageId) {
            log.warn("No new messages found");
            return;
        }
        var processedMessage = aiClient.processMessage(lastMessage.getMessage());
        telegramService.sendPrediction(processedMessage);
    }

}
