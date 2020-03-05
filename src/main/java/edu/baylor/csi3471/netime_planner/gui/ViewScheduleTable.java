package edu.baylor.csi3471.netime_planner.gui;

import edu.baylor.csi3471.netime_planner.models.Activity;
import edu.baylor.csi3471.netime_planner.models.Deadline;
import edu.baylor.csi3471.netime_planner.models.Event;
import edu.baylor.csi3471.netime_planner.models.TimeInterval;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ViewScheduleTable extends JTable {
    public ViewScheduleTable() {
        super(new ViewScheduleTableModel(testData(), LocalDate.now()));
    }

    private static List<Event> testData() {
        var list = new ArrayList<Event>();
        list.add(new Activity("Cool Activity", "Description", "Location", LocalDate.now(), new TimeInterval(LocalTime.of(3, 0), LocalTime.of(4, 0))));
        return list;
    }
}
