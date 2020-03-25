package edu.baylor.csi3471.netime_planner.gui;


import edu.baylor.csi3471.netime_planner.models.Controller;
import edu.baylor.csi3471.netime_planner.models.Event;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;
import java.util.Vector;
import java.time.temporal.ChronoUnit;

public class ViewScheduleTableModel extends AbstractTableModel {
    private Vector<String> theVector;
    private Event[][] events;
    //stores strings representing cells
    //keep list of events for potential future interaction
    private String[][][]Cells;
    private int[][] sizes;

    public ViewScheduleTableModel(Controller controller, LocalDate sDate){

        List<Event> b = controller.getEvents();

        theVector = new Vector<>();
        theVector.add("Sunday");
        theVector.add("Monday");
        theVector.add("Tuesday");
        theVector.add("Wednesday");
        theVector.add("Thursday");
        theVector.add("Friday");
        theVector.add("Saturday");
        events = new Event[48][7];
        Cells = new String[48][7][5];
        sizes = new int[48][7];
        for(int r = 0; r< Cells.length;r++){
            for(int c = 0;c < Cells[0].length; c++){
                Cells[r][c] = new String[5];
                sizes[r][c] = 0;
            }

        }
        for(Event r:b){
            System.out.println(r.getName());

            long dif = ChronoUnit.DAYS.between(r.getDay(),sDate);

            //System.out.println(dif);

            int weeks = (int)dif/7;

            int interv = 1;

            if(r.getOccurance() != 0){
                interv = interv % r.getOccurance();
            }

            if(weeks == 0 || interv == 0) {
                int[] days = r.findDayOccurance();
                double[] times = r.findPercentage();
                for (int d = 0; d < days.length; d++) {
                    if (times.length == 1) {
                        events[(int) (times[0] * events.length)][days[d]] = r;
                        Cells[(int) (times[0] * events.length)]
                                [days[d]]
                                [sizes[(int) (times[0] * events.length)][days[d]]] =
                                ((r.getName()) + " " + (int)(times[0]*events.length/2));
                        if((int) (times[0] * events.length) % 2 ==1){
                            Cells[(int) (times[0] * events.length)]
                                    [days[d]][sizes[(int) (times[0] * events.length)][days[d]]]
                                    += ":30";
                        }
                        else{
                            Cells[(int) (times[0] * events.length)][days[d]]
                                    [sizes[(int) (times[0] * events.length)][days[d]]]
                                    += ":00";
                        }

                        sizes[(int) (times[0] * events.length)][days[d]]++;
                    } else {
                        int start = (int) (times[0] * events.length);
                        int end = (int) (times[1] * events.length);
                        for (int i = start; i <= end; i++) {

                            events[i][days[d]] = r;
                            Cells[i][days[d]][sizes[i][days[d]]] = ((r.getName()) + " " + (i+1) / 2);
                            if((i+1) % 2 ==1){
                                Cells[i][days[d]][sizes[i][days[d]]] += ":30";
                            }
                            else{
                                Cells[i][days[d]][sizes[i][days[d]]] += ":00";
                            }
                            sizes[i][days[d]]++;
                        }
                    }
                }
            }

        }

    }
    public int getRowCount() {
        //System.out.println(rowData.size());
        return events.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        //System.out.println("a");
        if(events[rowIndex][columnIndex]== null)
            return "";

        String temp = "";
        Object value = null;

        String[] data = Cells[rowIndex][columnIndex];
        StringJoiner joiner = new StringJoiner("<br>", "<html>", "</html>");
        for (String text : data) {
            if(text!= null) {
                joiner.add(text);
            }
        }
        value = joiner.toString();

        return value;

    }
    public Event[][] getRowData(){
        return events;
    }
    public int getColumnCount() {
        return theVector.size();
    }
    public Class<?> getColumnClass(int c) {
        return String.class;
    }

    public String getColumnName(int c) {
        return theVector.get(c);
    }
    public boolean isCellEditable(int row, int col) {


        return true;
    }
    public void setValueAt(Object value, int row, int col) {

    }
}

