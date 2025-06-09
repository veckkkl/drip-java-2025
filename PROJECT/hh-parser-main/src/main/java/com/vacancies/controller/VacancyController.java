package com.vacancies.controller;

import com.vacancies.model.Vacancy;
import com.vacancies.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/update-vacancies")
    @ResponseBody
    public ResponseEntity<?> updateVacancies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String salary,
            @RequestParam(required = false) String employment,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Map<String, Object> result = vacancyService.updateVacancies(
                title, city, company, salary, employment, description, page, size
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }
} 