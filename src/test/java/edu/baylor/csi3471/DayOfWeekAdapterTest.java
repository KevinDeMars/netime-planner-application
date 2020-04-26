package edu.baylor.csi3471;

import edu.baylor.csi3471.netime_planner.models.adapters.DayOfWeekAdapter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.DayOfWeek;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DayOfWeekAdapterTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("generateData")
    void conversion(Set<DayOfWeek> days) {
        assertEquals(days, DayOfWeekAdapter.toSet(DayOfWeekAdapter.toInt(days)));
    }

    static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of(EnumSet.noneOf(DayOfWeek.class)),
                Arguments.of(EnumSet.of(DayOfWeek.MONDAY)),
                Arguments.of(EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)),
                Arguments.of(EnumSet.allOf(DayOfWeek.class)),
                Arguments.of(EnumSet.of(DayOfWeek.FRIDAY, DayOfWeek.SUNDAY))
        );
    }
}


