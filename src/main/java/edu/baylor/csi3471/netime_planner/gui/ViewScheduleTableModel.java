package edu.baylor.csi3471.netime_planner.gui;


import edu.baylor.csi3471.netime_planner.models.Event;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

public class ViewScheduleTableModel extends AbstractTableModel {
    private Vector<String> theVector;
    private Event[] events;

    public ViewScheduleTableModel(List<Event> b, LocalDate sDate){
        theVector = new Vector<String>();
        theVector.add("Sunday");
        theVector.add("Monday");
        theVector.add("Tuesday");
        theVector.add("Wednesday");
        theVector.add("Thursday");
        theVector.add("Friday");
        theVector.add("Saturday");
        events = new Event[48];
        for(Event r:b){

        }

    }
    public int getRowCount() {
        //System.out.println(rowData.size());
        return events.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        //System.out.println("a");
        if(events[rowIndex]== null)
            return "";
        return events[rowIndex].getName();
    }
    public Event[] getRowData(){
        return events;
    }
    public int getColumnCount() {
        return theVector.size();
    }
    public Class getColumnClass(int c) {
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