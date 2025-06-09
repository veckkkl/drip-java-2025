package com.vacancies.dto;

import lombok.Data;
import java.util.List;

@Data
public class HhVacancyResponse {
    private List<HhVacancy> items;

    @Data
    public static class HhVacancy {
        private String id;
        private String name;
        private Employer employer;
        private Area area;
        private Employment employment;
        private Salary salary;
        private String published_at;

        @Data
        public static class Employer {
            private String name;
        }

        @Data
        public static class Area {
            private String name;
        }

        @Data
        public static class Employment {
            private String name;
        }

        @Data
        public static class Salary {
            private Integer from;
            private Integer to;
        }
    }
} 