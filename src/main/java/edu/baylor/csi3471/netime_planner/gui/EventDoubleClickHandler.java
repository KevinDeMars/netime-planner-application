package edu.baylor.csi3471.netime_planner.gui;

import edu.baylor.csi3471.netime_planner.models.domain_objects.Event;

import java.util.List;

public interface EventDoubleClickHandler {
    void eventDoubleClicked(Event e);
    void multipleEventsDoubleClicked(List<Event> events);
}
