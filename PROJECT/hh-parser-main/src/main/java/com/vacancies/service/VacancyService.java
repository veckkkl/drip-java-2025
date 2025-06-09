package com.vacancies.service;

import java.util.List;
import java.util.Map;

public interface VacancyService {
    Map<String, Object> updateVacancies(String title, String city, String company, String salary, String employment, String description, int page, int size);
} 