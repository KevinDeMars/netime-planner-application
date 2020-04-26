package edu.baylor.csi3471.netime_planner.models;

import edu.baylor.csi3471.netime_planner.models.domain_objects.Event;

public interface ScheduleEventListener {
    void handleEventAdded(Event newEv);
    void handleEventRemoved(Event removedEv);
    void handleEventChanged(Event oldData, Event newData);
}
