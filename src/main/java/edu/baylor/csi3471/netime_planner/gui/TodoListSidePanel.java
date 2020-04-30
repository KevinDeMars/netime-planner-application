package edu.baylor.csi3471.netime_planner.gui;

import edu.baylor.csi3471.netime_planner.models.DayPercentageInterval;
import edu.baylor.csi3471.netime_planner.models.EventVisitor;
import edu.baylor.csi3471.netime_planner.models.TimeInterval;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Activity;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Deadline;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Event;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Schedule;

import javax.swing.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringJoiner;

class deadString{
    int precedence;
    Deadline deadline;


    public deadString(int precedence, Deadline deadline) {
        this.precedence = precedence;
        this.deadline = deadline;

    }
    public String toString(){
        String padded = String.format("%02d" , deadline.getDueDatetime().getMinute());
        return deadline.getName() +" @ "+ deadline.getDueDatetime().getHour()+":"+padded +" on " + deadline.getDueDatetime().getDayOfWeek();
    }
}

public class TodoListSidePanel extends JPanel {
    public TodoListSidePanel(Schedule s, LocalDate startDate){

        ArrayList<Event> list = (ArrayList<Event>) s.getEvents();
        ArrayList<deadString> deadList = new ArrayList<deadString>();
        String a;
        var visitor = new EventVisitor() {
            @Override
            public void visit(Deadline d) {
                var dueDate = LocalDate.from(d.getDueDatetime());
                // skip if not within this week
                if (dueDate.isAfter(startDate.plusDays(6)) || dueDate.isBefore(startDate))
                    return;

                // start and end of interval are both the due time
                var dueTimeIntv = new TimeInterval(LocalTime.from(d.getDueDatetime()), LocalTime.from(d.getDueDatetime()));
                var dayPercent = DayPercentageInterval.fromTimeInterval(dueTimeIntv);
                int rowIdx = (int)(dayPercent.getStart() * 100);
                // the enum goes from 1=Monday to 7=Sunday, so doing % 7 converts it to 0=Sunday to 6=Saturday
                int dayIdx = d.getDueDatetime().getDayOfWeek().getValue() % 7;
                dayIdx*=100000;
                dayIdx+=rowIdx+=rowIdx;

                deadString temp = new deadString(dayIdx,d);
                deadList.add(temp);

            }

            @Override
            public void visit(Activity a) {

            }
        };

        for(Event Ev:list) {
            Ev.acceptVisitor(visitor);
        }
        Collections.sort(deadList, new Comparator<deadString>() {
            @Override
            public int compare(deadString o1, deadString o2) {
                return o1.precedence-o2.precedence;
            }
        });
        StringJoiner joiner = new StringJoiner("<br>", "<html>", "</html>");
        StringBuilder endString = new StringBuilder();
        for(deadString st:deadList){
            joiner.add(st.toString());
        }

        add(new JLabel(joiner.toString()));


    }
    public TodoListSidePanel(){
        add(new JLabel("to do list"));



    }

    public void update(Schedule s, LocalDate startDate) {
        ArrayList<Event> list = (ArrayList<Event>) s.getEvents();
        ArrayList<deadString> deadList = new ArrayList<deadString>();
        String a;
        var visitor = new EventVisitor() {
            @Override
            public void visit(Deadline d) {
                var dueDate = LocalDate.from(d.getDueDatetime());
                // skip if not within this week
                if (dueDate.isAfter(startDate.plusDays(6)) || dueDate.isBefore(startDate))
                    return;

                // start and end of interval are both the due time
                var dueTimeIntv = new TimeInterval(LocalTime.from(d.getDueDatetime()), LocalTime.from(d.getDueDatetime()));
                var dayPercent = DayPercentageInterval.fromTimeInterval(dueTimeIntv);
                int rowIdx = (int)(dayPercent.getStart() * 100);
                // the enum goes from 1=Monday to 7=Sunday, so doing % 7 converts it to 0=Sunday to 6=Saturday
                int dayIdx = d.getDueDatetime().getDayOfWeek().getValue() % 7;
                dayIdx*=100000;
                dayIdx+=rowIdx+=rowIdx;

                deadString temp = new deadString(dayIdx,d);
                deadList.add(temp);

            }

            @Override
            public void visit(Activity a) {

            }
        };

        for(Event Ev:list) {
            Ev.acceptVisitor(visitor);
        }
        Collections.sort(deadList, new Comparator<deadString>() {
            @Override
            public int compare(deadString o1, deadString o2) {
                return o1.precedence-o2.precedence;
            }
        });
        StringJoiner joiner = new StringJoiner("<br>", "<html>", "</html>");
        StringBuilder endString = new StringBuilder();
        for(deadString st:deadList){
            joiner.add(st.toString());
        }
        remove(0);
        add(new JLabel(joiner.toString()));
    }
}
