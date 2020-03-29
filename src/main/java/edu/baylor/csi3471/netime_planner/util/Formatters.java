package edu.baylor.csi3471.netime_planner.util;

import java.time.format.DateTimeFormatter;

public class Formatters {
    public static final DateTimeFormatter TWELVE_HOURS = DateTimeFormatter.ofPattern("h:mm a");
    public static final DateTimeFormatter LONG_DATE = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss");
}
