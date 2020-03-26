package edu.baylor.csi3471.netime_planner.gui;


import edu.baylor.csi3471.netime_planner.models.Controller;
import edu.baylor.csi3471.netime_planner.models.Event;
import edu.baylor.csi3471.netime_planner.util.Formatters;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ViewScheduleTableModel extends AbstractTableModel {
    private static final List<String> columnNames = List.of("Time", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");

    // Each List<Event> is a cell that stores all events within a given 30-minute period
    // within a given day
    private List<List<List<Event>>> Cells;

    public ViewScheduleTableModel(Controller controller, LocalDate sDate){
        List<Event> events = controller.getEvents();

        Cells = initMatrix(7, 48);

        for(Event event : events){
            System.out.println(event.getName());

            long dif = ChronoUnit.DAYS.between(event.getDay(), sDate);

            //System.out.println(dif);

            int weeks = (int)dif/7;

            int interv = 1;

            if(event.getOccurance() != 0){
                interv = interv % event.getOccurance();
            }

            if(weeks == 0 || interv == 0) {
                int[] days = event.findDayOccurance();
                double[] times = event.findPercentage();
                int rowIdx = (int) (times[0] * Cells.size());
                for (int day : days) {
                    if (times.length == 1) {
                        Cells.get(rowIdx)
                                .get(day)
                                .add(event);
                    } else {
                        int lastRowIdx = (int) (times[1] * Cells.size());
                        for (int i = rowIdx; i <= lastRowIdx; i++) {
                            Cells.get(i).get(day).add(event);
                        }
                    }
                }
            }

        }

    }

    private static <T> List<List<List<T>>> initMatrix(int numRows, int numCols) {
        var result = new ArrayList<List<List<T>>>(numRows);
        for(int r = 0; r < numRows; r++){
            result.add(new ArrayList<>(numCols));
            for(int c = 0; c < numCols; c++){
                result.get(r).add(new ArrayList<>());
            }
        }
        return result;
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

        /*
        List<Event> data = Cells.get(rowIndex).get(columnIndex - 1);
        StringJoiner joiner = new StringJoiner("<br>", "<html>", "</html>");
        for (Event ev : data) {
            joiner.add(ev.getName());
        }
        return joiner.toString();*/
        return Cells.get(rowIndex).get(columnIndex);

    }

    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Class<?> getColumnClass(int c) {
        if (c == 0)
            return String.class;
        else
            return List.class;
    }

    public String getColumnName(int c) {
        return columnNames.get(c);
    }
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}

