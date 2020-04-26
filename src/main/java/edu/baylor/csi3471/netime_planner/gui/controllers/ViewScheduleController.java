package edu.baylor.csi3471.netime_planner.gui.controllers;

import edu.baylor.csi3471.netime_planner.models.domain_objects.Event;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Schedule;
import edu.baylor.csi3471.netime_planner.services.ScheduleService;
import edu.baylor.csi3471.netime_planner.services.ServiceManager;

public class ViewScheduleController {
    private final ScheduleService scheduleSvc = ServiceManager.getInstance().getService(ScheduleService.class);
    private final Schedule schedule;

    public ViewScheduleController(Schedule s) {
        schedule = s;
    }

    public void addEvent(Event e) {
        scheduleSvc.addEvent(schedule, e);
    }
    public void changeEvent(Event oldVal, Event newVal) {
        scheduleSvc.changeEvent(schedule, oldVal, newVal);
    }
    public void removeEvent(Event e) {
        scheduleSvc.removeEvent(schedule, e);
    }
}
