package com.genseck.uvb76.predictor.wathcer.configuration;

import com.genseck.uvb76.predictor.wathcer.properties.UVB76ClientProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@RequiredArgsConstructor
@Configuration
@Import(UVB76ClientProperties.class)
public class WatcherConfiguration {}
