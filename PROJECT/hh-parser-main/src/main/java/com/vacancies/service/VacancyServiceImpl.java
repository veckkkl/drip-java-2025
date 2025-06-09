package com.vacancies.service;

import com.vacancies.model.Vacancy;
import com.vacancies.parser.VacancyParser;
import com.vacancies.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;
    private final List<VacancyParser> parsers;

    @Override
    public Map<String, Object> updateVacancies(String title, String city, String company, String salary, String employment, String description, int page, int size) {
        log.info("Starting to update vacancies");
        vacancyRepository.deleteAll();
        List<Vacancy> allVacancies = new ArrayList<>();

        for (VacancyParser parser : parsers) {
            log.info("Fetching vacancies from {}", parser.getSourceName());
            try {
                for (int p = 0; p < 10; p++) {
                    List<Vacancy> vacancies = parser.parseVacancies("python", p);
                    vacancies.forEach(v -> v.setSourceName(parser.getSourceName()));
                    allVacancies.addAll(vacancies);
                }
            } catch (Exception e) {
                log.error("Error fetching vacancies from {}: {}", parser.getSourceName(), e.getMessage(), e);
            }
        }

        log.info("Saving {} vacancies to database", allVacancies.size());
        vacancyRepository.saveAll(allVacancies);

        // Filtering logic
        List<Map<String, Object>> filtered = allVacancies.stream()
            .filter(v -> title == null || title.isEmpty() || (v.getTitle() != null && (v.getTitle().toLowerCase().contains(title.toLowerCase()) || (v.getDescription() != null && v.getDescription().toLowerCase().contains(title.toLowerCase())))))
            .filter(v -> city == null || city.isEmpty() || (v.getLocation() != null && v.getLocation().toLowerCase().contains(city.toLowerCase())))
            .filter(v -> company == null || company.isEmpty() || (v.getEmployer() != null && v.getEmployer().toLowerCase().contains(company.toLowerCase())))
            .filter(v -> salary == null || salary.isEmpty() || (v.getSalary() != null && parseSalary(v.getSalary()) >= parseSalary(salary)))
            .filter(v -> employment == null || employment.isEmpty() || (v.getEmployment() != null && v.getEmployment().toLowerCase().contains(employment.toLowerCase())))
            .filter(v -> description == null || description.isEmpty() || (v.getDescription() != null && v.getDescription().toLowerCase().contains(description.toLowerCase())))
            .map(vacancy -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", vacancy.getId());
                map.put("title", vacancy.getTitle());
                map.put("employer", vacancy.getEmployer());
                map.put("location", vacancy.getLocation());
                map.put("employment", vacancy.getEmployment());
                map.put("salary", vacancy.getSalary());
                map.put("publishedAt", vacancy.getPublishedAt());
                map.put("source", vacancy.getSource());
                map.put("link", vacancy.getLink());
                map.put("sourceUrl", vacancy.getSourceUrl());
                map.put("description", vacancy.getDescription());
                map.put("requirements", vacancy.getRequirements());
                map.put("sourceName", vacancy.getSourceName());
                map.put("vacancyId", vacancy.getVacancyId());
                return map;
            })
            .collect(Collectors.toList());

        int total = filtered.size();
        int fromIndex = Math.max(0, Math.min(page * size, total));
        int toIndex = Math.max(0, Math.min(fromIndex + size, total));
        List<Map<String, Object>> paged = filtered.subList(fromIndex, toIndex);

        Map<String, Object> result = new HashMap<>();
        result.put("data", paged);
        result.put("total", total);
        result.put("page", page);
        result.put("size", size);
        return result;
    }

    // Helper to parse salary string to int (simple version)
    private int parseSalary(String salary) {
        if (salary == null) return 0;
        try {
            String digits = salary.replaceAll("[^0-9]", "");
            return digits.isEmpty() ? 0 : Integer.parseInt(digits);
        } catch (Exception e) {
            return 0;
        }
    }
} 