package com.genseck.uvb76.predictor.wathcer;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genseck.uvb76.predictor.wathcer.dto.UVB76LogsDto;
import com.genseck.uvb76.predictor.wathcer.properties.UVB76ClientProperties;
import jakarta.annotation.PostConstruct;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class UVBReaderService {

    private final UVB76ClientProperties uvb76ClientProperties;
    private WebClient client;

    @PostConstruct
    void init() {
        String url = uvb76ClientProperties.getBaseUrl() + uvb76ClientProperties.getChannel() + "?limit=1";
        this.client = WebClient.create(url);
    }

    public UVB76LogsDto getLatestPosts() {
        return getLatestPostsInner(true);
    }

    public UVB76LogsDto getLatestPostsInner(boolean isTest) {

        //TODO: Remove!
        if (isTest) {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            try {
                URL url = classloader.getResource("example.json");
                Path path = Paths.get(url.toURI());
                var line = Files.readAllLines(path, StandardCharsets.UTF_8).stream().collect(Collectors.joining());
                var om = new ObjectMapper();
                om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                var parsedResponse = om.readValue(line, UVB76LogsDto.class);
                return parsedResponse;

            } catch (Exception e) {
                log.error("Could not parse data from UVB76", e);
                throw new RuntimeException(e);
            }
        }
        try {
            var rq = client.get();
            var response = rq.retrieve();
            var rString = response.bodyToMono(String.class).block();
            var om = new ObjectMapper();
            var parsedResponse = om.readValue(rString, UVB76LogsDto.class);
            return parsedResponse;
        } catch (Exception e) {
            log.error("Could not get data from UVB76", e);
            throw new RuntimeException(e);
        }
    }

}
