package com.vacancies.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "vacancy.sources")
public class VacancySourceConfig {
    private List<Source> sources;

    @Data
    public static class Source {
        private String name;
        private String baseUrl;
        private String searchPath;
        private Map<String, String> headers;
        private Map<String, String> selectors;
        private boolean enabled;
    }
} 