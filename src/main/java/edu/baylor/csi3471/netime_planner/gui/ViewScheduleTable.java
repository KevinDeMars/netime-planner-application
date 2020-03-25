package edu.baylor.csi3471.netime_planner.gui;

import edu.baylor.csi3471.netime_planner.models.Activity;
import edu.baylor.csi3471.netime_planner.models.Controller;
import edu.baylor.csi3471.netime_planner.models.Event;
import edu.baylor.csi3471.netime_planner.models.TimeInterval;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ViewScheduleTable extends JTable {
    private Controller controller;

    //public ViewScheduleTable(Controller controller) {
      //  super(new ViewScheduleTableModel(testData(), LocalDate.now()));
    //}
    public ViewScheduleTable(Controller controller) {
       super(new ViewScheduleTableModel(controller,LocalDate.now()));

       this.setRowHeight(controller.getMaxSize()*30);
    }



    private static List<Event> testData() {
        var list = new ArrayList<Event>();
        list.add(new Activity("Cool activity", "Description", "Location", LocalDate.now(), new TimeInterval(LocalTime.of(3, 0), LocalTime.of(5, 0))));
        list.add(new Activity("negactivity", "Description", "Location", LocalDate.now(), new TimeInterval(LocalTime.of(3, 0), LocalTime.of(5, 0))));

        return list;
    }
}
