package edu.baylor.csi3471.netime_planner;

import edu.baylor.csi3471.netime_planner.models.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Event> events = Arrays.asList(new Deadline("Group Project",
                "Finish skeleton before meeting",
                null,
                LocalDateTime.of(2020, 2, 27, 16, 0),
                null,
                null),
                new Deadline("Algorithms Homework",
                        "Weird modulus stuff",
                        null,
                        LocalDateTime.of(2020, 2, 24, 23, 59),
                        null,
                        null),
                new Activity("Software Engineering", "Cerninator", null,
                        new TimeInterval(LocalTime.of(9, 30), LocalTime.of(10, 45)),
                        EnumSet.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY),
                        LocalDate.of(2020, 2, 1),
                        LocalDate.of(2020, 3, 31),
                        1)
        );

        events.forEach(System.out::println);

        var schedule = new Schedule();
        schedule.getEvents().addAll(events);
        var todo = schedule.makeToDoList(new DateTimeInterval(
                LocalDateTime.of(2020, 2, 24, 0, 0),
                LocalDateTime.of(2020, 2, 24, 23, 59)
        ));
        System.out.println("\n\nThings to do on 2/24:");
        todo.forEach(System.out::println);

        todo = schedule.makeToDoList(new DateTimeInterval(
                LocalDateTime.of(2020, 2, 24, 0, 0),
                LocalDateTime.of(2020, 2, 29, 23, 59)
        ));
        System.out.println("\n\nThings to do from 2/24 through 2/29:");
        todo.forEach(System.out::println);
	//JFrame theFrame = new JFrame();

        //JTable table = new JTable(new ViewTable(events,dt));
        //theFrame.add(new JScrollPane(table));
        //theFrame.setTitle("Your Week at a Glance");
        //theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //theFrame.pack();
        //theFrame.setVisible(true);
    }
}
