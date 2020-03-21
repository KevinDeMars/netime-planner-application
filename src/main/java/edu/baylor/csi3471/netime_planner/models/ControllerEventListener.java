package edu.baylor.csi3471.netime_planner.models;

public interface ControllerEventListener {
    void handleEventAdded(Event newEv);
    void handleEventRemoved(Event removedEv);
    void handleEventChanged(Event oldData, Event newData);
}
