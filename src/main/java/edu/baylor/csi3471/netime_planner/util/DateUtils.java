package edu.baylor.csi3471.netime_planner.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
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
     * Gets the last Sunday, from the given day (which could be the given day).
     * @return The previous Sunday.
     */
    public static LocalDate getLastSunday(LocalDate day) {
        while (day.getDayOfWeek() != DayOfWeek.SUNDAY) {
            day = day.plusDays(-1);
        }
        return day;
    }

    /**
     * Gets the last Sunday from today (which could be today).
     * @return The previous Sunday.
     */
    public static LocalDate getLastSunday() {
        return getLastSunday(LocalDate.now());
    }
    
    /**
     * Constructs a set of DayOfWeek objects from those which are passed into the method.
     * @param days The DayOfWeek objects which the method will create a set from.
     * @return The set of DayOfWeek objects from those which were passed into the method.
     */
	public static Set<DayOfWeek> weekDaySet(DayOfWeek...days){
        return new HashSet<>(Arrays.asList(days));
	}
}
