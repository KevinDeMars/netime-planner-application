package edu.baylor.csi3471.netime_planner.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public class DateUtils {
    /**
     * Given a day and a set of weekdays, finds the next day that is in the valid weekdays.
     * @param day The current day.
     * @param weekDays Which week days to look for.
     * @return The first valid day after the given day that is in the given weekdays.
     */
    public static LocalDate getNextWeekDay(LocalDate day, Set<DayOfWeek> weekDays) {
        // Go to next day so it doesn't return the same day
        day = day.plusDays(1);
        // add a day until it's a valid weekday
        while (!weekDays.contains(day.getDayOfWeek()))
            day = day.plusDays(1);
        return day;
    }

    /**
     * Gets the last Sunday (which could be the current date).
     * @return The previous Sunday.
     */
    public static LocalDate getLastSunday() {
        var day = LocalDate.now();
        while (day.getDayOfWeek() != DayOfWeek.SUNDAY) {
            day = day.plusDays(-1);
        }
        return day;
    }
}
