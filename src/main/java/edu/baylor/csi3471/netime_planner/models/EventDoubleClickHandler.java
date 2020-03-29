package edu.baylor.csi3471.netime_planner.models;

import java.util.List;

public interface EventDoubleClickHandler {
    void eventDoubleClicked(Event e);
    void multipleEventsDoubleClicked(List<Event> events);
}
