package edu.baylor.csi3471.netime_planner.gui;


import edu.baylor.csi3471.netime_planner.models.Controller;
import edu.baylor.csi3471.netime_planner.models.ControllerEventListener;
import edu.baylor.csi3471.netime_planner.models.Event;
import edu.baylor.csi3471.netime_planner.util.Formatters;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ViewScheduleTableModel extends AbstractTableModel implements ControllerEventListener {
    private static final List<String> columnNames = List.of("Time", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");

    // Each List<Event> is a cell that stores all events within a given 30-minute period
    // within a given day
    private List<List<List<Event>>> Cells;
    Controller controller;
    LocalDate sDate;

    public ViewScheduleTableModel(Controller controller, LocalDate sDate){

        List<Event> events = controller.getEvents();
        controller.addEventListener(this);
        controller.setMaxSize(1);
        this.controller = controller;
        this.sDate = sDate;


        Cells = new ArrayList<>();
        for(int r = 0; r < 48; r++){
            Cells.add(new ArrayList<>(7));
            for(int c = 0; c < 7; c++){
                Cells.get(r).add(new ArrayList<>());
            }
        }

        for(Event event : events){
            add(event);
        }

    }
    public int getRowCount() {
        //System.out.println(rowData.size());
        return Cells.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            int hour = rowIndex / 2;
            int minute = 30 * (rowIndex % 2);
            var time = LocalTime.of(hour, minute);
            return time.format(Formatters.TWELVE_HOURS) + " - " + time.plusMinutes(29).format(Formatters.TWELVE_HOURS);
        }

        String temp = "";
        Object value = null;

        List<Event> data = Cells.get(rowIndex).get(columnIndex - 1);
        StringJoiner joiner = new StringJoiner("<br>", "<html>", "</html>");
        for (Event ev : data) {
            joiner.add(ev.getName());
        }
        value = joiner.toString();

        return value;

    }
    private void add(Event newEv) {
        System.out.println(newEv.getName());

        long dif = ChronoUnit.DAYS.between(newEv.getDay(), sDate);

        //System.out.println(dif);

        int weeks = (int)dif/7;

        int interv = 1;

        if(newEv.getOccurance() != -1 && newEv.getOccurance()!= 0){
            interv = interv % newEv.getOccurance();
        }

        if(weeks == 0 || interv == 0) {
            int[] days = newEv.findDayOccurance();
            double[] times = newEv.findPercentage();
            int rowIdx = (int) (times[0] * Cells.size());
            for (int day : days) {
                if (times.length == 1) {
                    Cells.get(rowIdx)
                            .get(day)
                            .add(newEv);
                    if(Cells.get(rowIdx).size()>controller.getMaxSize()){
                        controller.setMaxSize(Cells.get(rowIdx).get(day).size());
                    }
                } else {
                    int lastRowIdx = (int) (times[1] * Cells.size());
                    for (int i = rowIdx; i <= lastRowIdx; i++) {
                        Cells.get(i).get(day).add(newEv);
                        if(Cells.get(i).size()>controller.getMaxSize()){
                            controller.setMaxSize(Cells.get(i).get(day).size());
                        }
                    }
                }
            }
        }


    }
    public void remove(Event newEv) {
        long dif = ChronoUnit.DAYS.between(newEv.getDay(), sDate);

        //System.out.println(dif);

        int weeks = (int)dif/7;

        int interv = 1;

        if(newEv.getOccurance() != -1 && newEv.getOccurance()!= 0){
            interv = interv % newEv.getOccurance();
        }

        if(weeks == 0 || interv == 0) {
            int[] days = newEv.findDayOccurance();
            double[] times = newEv.findPercentage();
            int rowIdx = (int) (times[0] * Cells.size());
            for (int day : days) {
                if (times.length == 1) {
                    Cells.get(rowIdx)
                            .get(day)
                            .remove(newEv);

                } else {
                    int lastRowIdx = (int) (times[1] * Cells.size());
                    for (int i = rowIdx; i <= lastRowIdx; i++) {

                        Cells.get(i).get(day).remove(newEv);
                    }
                }
            }
        }
    }

    public void change(Event oldData, Event newData) {
        remove(oldData);
        add(newData);
    }

    public void handleEventAdded(Event newEv) {
        add(newEv);
        fireTableDataChanged();

        System.out.println("new event");

    }

    public void handleEventRemoved(Event removedEv) {
        remove(removedEv);
        fireTableDataChanged();

        System.out.println("old event");

    }

    public void handleEventChanged(Event oldData, Event newData) {
        change(oldData,newData);
        fireTableDataChanged();
        System.out.println("change event");

    }

    public int getColumnCount() {
        return columnNames.size();
    }
    public Class<?> getColumnClass(int c) {
        return String.class;
    }

    public String getColumnName(int c) {
        return columnNames.get(c);
    }
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}

