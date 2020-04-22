package edu.baylor.csi3471.netime_planner.models.adapters;

import java.time.DayOfWeek;
import java.util.EnumSet;
import java.util.Set;

public class DayOfWeekAdapter {
    public static int toInt(Set<DayOfWeek> s) {
        int result = 0;
        for (DayOfWeek d : s) {
            result |= (1 << d.getValue());
        }
        return result;
    }

    public static Set<DayOfWeek> toSet(int x) {
        Set<DayOfWeek> result = EnumSet.noneOf(DayOfWeek.class);
        int val = 0;
        while (x > 0) {
            if ((x & 1) == 1) {
                result.add(DayOfWeek.of(val));
            }

            x >>= 1;
            val++;
        }

        return result;
    }
}
