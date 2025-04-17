package homework4.task2;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FridayTheThirteenthFinder {

    public static List<String> findFridayTheThirteenth(int year) {
        List<String> fridays = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int month = 1; month <= 12; month++) {
            LocalDate date = LocalDate.of(year, month, 13);
            if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                fridays.add(date.format(formatter));
            }
        }
        return fridays;
    }

    public static LocalDate findNextFridayTheThirteenth(LocalDate date) {
        LocalDate next13th = date.withDayOfMonth(13);
        if (next13th.isBefore(date) || next13th.isEqual(date)) {
            next13th = next13th.plusMonths(1);
        }

        while (next13th.getDayOfWeek() != DayOfWeek.FRIDAY) {
            next13th = next13th.plusMonths(1).withDayOfMonth(13);
        }
        return next13th;
    }
}