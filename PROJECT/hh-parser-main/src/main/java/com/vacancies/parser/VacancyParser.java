package com.vacancies.parser;

import com.vacancies.model.Vacancy;
import java.util.List;

public interface VacancyParser {
    List<Vacancy> parseVacancies(String searchQuery, int page);
    Vacancy parseVacancyDetails(String vacancyUrl);
    String getSourceName();
} 