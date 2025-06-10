package com.vacancies.parser;

import com.vacancies.config.VacancySourceConfig;
import com.vacancies.dto.HhVacancyResponse;
import com.vacancies.model.Vacancy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HhRuParser implements VacancyParser {
    private final WebClient webClient;
    private final VacancySourceConfig sourceConfig;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

    @Override
    public List<Vacancy> parseVacancies(String searchQuery, int page) {
        List<Vacancy> vacancies = new ArrayList<>();
        try {
            Mono<HhVacancyResponse> responseMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/vacancies")
                    .queryParam("text", searchQuery)
                    .queryParam("page", page)
                    .queryParam("per_page", 100)
                    .build())
                .retrieve()
                .bodyToMono(HhVacancyResponse.class);

            HhVacancyResponse response = responseMono.block();
            if (response != null && response.getItems() != null) {
                response.getItems().forEach(hhVacancy -> {
                    try {
                        Vacancy vacancy = new Vacancy();
                        vacancy.setTitle(hhVacancy.getName());
                        vacancy.setEmployer(hhVacancy.getEmployer().getName());
                        vacancy.setLocation(hhVacancy.getArea().getName());
                        vacancy.setEmployment(hhVacancy.getEmployment().getName());
                        vacancy.setVacancyId(Integer.parseInt(hhVacancy.getId()));
                        vacancy.setSourceUrl("https://hh.ru/vacancy/" + hhVacancy.getId());
                        
                        if (hhVacancy.getSalary() != null) {
                            String salary = String.format("%d ₽", 
                                hhVacancy.getSalary().getFrom() != null ? hhVacancy.getSalary().getFrom() : 0);
                            vacancy.setSalary(salary);
                        }

                        try {
                            String publishedAtStr = hhVacancy.getPublished_at();
                            String normalizedDate = publishedAtStr;
                            if (publishedAtStr.endsWith("+0300")) {
                                normalizedDate = publishedAtStr.replace("+0300", "+03:00");
                            } else if (!publishedAtStr.endsWith("+03:00")) {
                                normalizedDate = publishedAtStr + "+03:00";
                            }
                            LocalDateTime publishedAt = LocalDateTime.parse(normalizedDate, DateTimeFormatter.ISO_DATE_TIME);
                            vacancy.setPublishedAt(publishedAt);
                        } catch (Exception e) {
                            log.error("Error parsing date for vacancy {}: {}. Date string was: {}", 
                                hhVacancy.getId(), e.getMessage(), hhVacancy.getPublished_at());
                            vacancy.setPublishedAt(LocalDateTime.now());
                        }

                        vacancy.setSource("HH.ru");
                        vacancy.setLink("https://hh.ru/vacancy/" + hhVacancy.getId());
                        vacancies.add(vacancy);
                    } catch (Exception e) {
                        log.error("Error processing vacancy {}: {}", hhVacancy.getId(), e.getMessage());
                    }
                });
            }
        } catch (Exception e) {
            log.error("Error fetching vacancies from HH.ru: {}", e.getMessage(), e);
        }
        return vacancies;
    }

    @Override
    public Vacancy parseVacancyDetails(String vacancyUrl) {
        // TODO: Implement detailed vacancy parsing
        return null;
    }

    @Override
    public String getSourceName() {
        return "HH.ru";
    }
} 