package homework4.task1;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ComputerClubAnalytics {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");

        long totalSeconds = 0;
        int sessionCount = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;

            String[] parts = line.split(" - ");
            LocalDateTime start = LocalDateTime.parse(parts[0], formatter);
            LocalDateTime end = LocalDateTime.parse(parts[1], formatter);
            Duration duration = Duration.between(start, end);
            totalSeconds += duration.getSeconds();
            sessionCount++;
        }

        if (sessionCount == 0) {
            System.out.println("Нет данных");
            return;
        }

        long averageSeconds = totalSeconds / sessionCount;
        long hours = averageSeconds / 3600;
        long minutes = (averageSeconds % 3600) / 60;

        System.out.println(hours + "ч " + minutes + "м");
    }
}