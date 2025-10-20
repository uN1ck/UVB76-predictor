package com.genseck.uvb76.predictor.service;

import com.genseck.uvb76.predictor.ai.AiClient;
import com.genseck.uvb76.predictor.telegram.TelegramService;
import com.genseck.uvb76.predictor.wathcer.UVBReaderService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        var parsedMessages = parseMessage(lastMessage.getMessage());

        //TODO: NPE!
        var processedMessage = aiClient.processMessage(parsedMessages.get(0));
        telegramService.sendPrediction(processedMessage);
    }

    public static List<String> parseMessage(String text) {
        // Извлекаем сообщение, начинающееся с НЖТИ и заканчивающееся на группу цифр
        Pattern pattern = Pattern.compile(
                "(НЖТИ .+?<br>)"
        );

        Matcher matcher = pattern.matcher(text);

        var results = new ArrayList<String>();
        while (matcher.find()) {
            String message = matcher.group()
                    .replaceAll("<.*?>", "") // убрать HTML-теги
                    .trim();
            results.add(message);
        }

        return results;
    }

}
