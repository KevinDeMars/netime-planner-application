package edu.baylor.csi3471.netime_planner.gui;


import edu.baylor.csi3471.netime_planner.models.*;
import edu.baylor.csi3471.netime_planner.util.Formatters;

import javax.swing.table.AbstractTableModel;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Logger;

public class ViewScheduleTableModel extends AbstractTableModel implements ControllerEventListener {
    private static Logger LOGGER = Logger.getLogger(ViewScheduleTableModel.class.getName());
    private static final List<String> columnNames = List.of("Time", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");

    // Each List<Event> is a cell that stores all events within a given 30-minute period
    // within a given day
    private List<List<List<Event>>> Cells;
    Controller controller;
    LocalDate startDate;

    public ViewScheduleTableModel(Controller controller, LocalDate startDate){

        List<Event> events = controller.getEvents();
        controller.addEventListener(this);
        controller.setMaxSize(1);
        this.controller = controller;
        this.startDate = startDate;


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

    public List<Event> getEventsAt(int row, int col) {
        return Cells.get(row).get(col - 1);
    }

    private void add(Event newEv) {
        var visitor = new EventVisitor() {
            @Override
            public void visit(Deadline d) {
                var dueDate = LocalDate.from(d.getDueDateTime());
                // skip if not within this week
                if (dueDate.isAfter(startDate.plusDays(6)) || dueDate.isBefore(startDate))
                    return;

                // start and end of interval are both the due time
                var dueTimeIntv = new TimeInterval(LocalTime.from(d.getDueDateTime()), LocalTime.from(d.getDueDateTime()));
                var dayPercent = DayPercentageInterval.fromTimeInterval(dueTimeIntv);
                int rowIdx = (int)(dayPercent.getStart() * Cells.size());
                // the enum goes from 1=Monday to 7=Sunday, so doing % 7 converts it to 0=Sunday to 6=Saturday
                int dayIdx = d.getDueDateTime().getDayOfWeek().getValue() % 7;
                Cells.get(rowIdx).get(dayIdx).add(d);
            }

            @Override
            public void visit(Activity a) {
                // Skip if not on this week
                long weeksSinceStart = ChronoUnit.WEEKS.between(a.getStartDate(), startDate);
                if (a.getEndDate().isPresent() && a.getEndDate().get().isBefore(startDate)
                    || (a.getStartDate().isAfter(startDate.plusDays(6))))
                {
                    LOGGER.info("Skipped " + a.getName() + " because it ended earlier or stated later than " + startDate);
                    return;
                }
                if (a.getWeekInterval().isPresent() && weeksSinceStart % a.getWeekInterval().get() != 0)
                {
                    LOGGER.info("Skipped " + a.getName() + " because of weekinterval");
                    return;
                }

                var dayPercentIntv = DayPercentageInterval.fromTimeInterval(a.getTime());
                int firstRowIdx = (int)(dayPercentIntv.getStart() * Cells.size());
                int lastRowIdx = (int)(dayPercentIntv.getEnd() * Cells.size());
                for (DayOfWeek dow : a.getDays()) {
                    int dayIdx = dow.getValue() % 7; // See above
                    for (int rowIdx = firstRowIdx; rowIdx <= lastRowIdx; ++rowIdx) {
                        Cells.get(rowIdx).get(dayIdx).add(a);
                        LOGGER.fine("Added " + a.getName() + " to (" + rowIdx + ", " + dayIdx + ")");
                    }
                }
            }
        };

        newEv.visit(visitor);

    }
    public void remove(Event ev) {
        for (var row : Cells) {
            for (var cell : row) {
                cell.remove(ev);
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

        LOGGER.info("New event added: " + newEv.getName());

    }

    public void handleEventRemoved(Event removedEv) {
        remove(removedEv);
        fireTableDataChanged();

        LOGGER.info("Removed event: " + removedEv.getName());

    }

    public void handleEventChanged(Event oldData, Event newData) {
        change(oldData,newData);
        fireTableDataChanged();
        LOGGER.info("Modified event: " + newData.getName());
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

