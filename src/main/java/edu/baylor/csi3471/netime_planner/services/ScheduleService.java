package edu.baylor.csi3471.netime_planner.services;

import edu.baylor.csi3471.netime_planner.models.ScheduleEventListener;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Event;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Schedule;
import edu.baylor.csi3471.netime_planner.models.domain_objects.User;
import edu.baylor.csi3471.netime_planner.models.persistence.ScheduleDAO;
import edu.baylor.csi3471.netime_planner.models.persistence.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class ScheduleService {
    private ScheduleDAO scheduleDAO() {
        return ServiceManager.getInstance().getService(ScheduleDAO.class);
    }

    private final List<ScheduleEventListener> listeners = new ArrayList<>();

    public void addEvent(Schedule s, Event e) {
        s.addEvent(e);
        scheduleDAO().save(s);
        listeners.forEach(l -> l.handleEventAdded(e));
    }
    public void removeEvent(Schedule s, Event e) {
        s.removeEvent(e);
        scheduleDAO().save(s);
        listeners.forEach(l -> l.handleEventRemoved(e));
    }
    public void changeEvent(Schedule s, Event oldData, Event e) {
        s.removeEvent(oldData);
        s.addEvent(e);
        scheduleDAO().save(s);
        listeners.forEach(l -> l.handleEventChanged(oldData, e));
    }

    public Schedule getSchedule(String username) {
        var mgr = ServiceManager.getInstance();
        var user = mgr.getService(UserDAO.class).findByUsername(username);
        return user.map(User::getSchedule).orElse(null);
    }

    public void listenToChanges(ScheduleEventListener l) {
        listeners.add(l);
    }
}
