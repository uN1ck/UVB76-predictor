package com.genseck.uvb76.predictor.ai.service;

import chat.giga.client.ResponseHandler;
import chat.giga.model.completion.CompletionChunkResponse;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AiResponseHandler implements ResponseHandler<CompletionChunkResponse> {

    private final Consumer<String> handleCompleteResponse;
    private final StringBuffer response = new StringBuffer();

    @Override
    public void onNext(CompletionChunkResponse chunk) {
        log.warn("Chunk: {}", chunk);
        response.append(chunk.choices().get(0).delta().content());
    }

    @Override
    public void onComplete() {
        var message = response.toString();
        log.warn("Message: {}", message);
        handleCompleteResponse.accept(message);
    }

    @Override
    public void onError(Throwable th) {
        log.error("Error on trying to get response from GigaChat", th);
    }
}
