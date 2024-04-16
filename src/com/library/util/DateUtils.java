package com.library.util;

import java.time.LocalDate;
import java.time.Period;

public class DateUtils {
    private DateUtils() {
        // Private constructor to prevent instantiation
    }

    public static boolean isDateOverdue(LocalDate returnDate) {
        LocalDate currentDate = LocalDate.now();
        return returnDate.isBefore(currentDate);
    }

    public static int getDaysBetween(LocalDate date1, LocalDate date2) {
        Period period = Period.between(date1, date2);
        return Math.abs(period.getDays());
    }

    public static String formatDate(LocalDate date) {
        return date.toString();
    }
}