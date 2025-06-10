package com.vacancies.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class WebClientConfig {

    @Value("${HH_API_BASE_URL:https://api.hh.ru}")
    private String baseUrl;

    @Value("${HH_API_USER_AGENT:Mozilla/5.0}")
    private String userAgent;

    @Bean
    public WebClient webClient() {
        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();
        return WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("User-Agent", userAgent)
            .defaultHeader("Accept", "application/json")
            .codecs(configurer -> {
                configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024); // 16MB
                configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(mapper));
                configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(mapper));
            })
            .build();
    }
} 