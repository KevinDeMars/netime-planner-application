package edu.baylor.csi3471.netime_planner.services;

import edu.baylor.csi3471.netime_planner.models.domain_objects.Event;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Schedule;
import edu.baylor.csi3471.netime_planner.models.domain_objects.User;
import edu.baylor.csi3471.netime_planner.models.persistence.ScheduleDAO;
import edu.baylor.csi3471.netime_planner.models.persistence.UserDAO;

public class ScheduleService {
    private ScheduleDAO scheduleDAO() {
        return ServiceManager.getInstance().getService(ScheduleDAO.class);
    }

    public void addEvent(Schedule s, Event e) {
        s.addEvent(e);
        scheduleDAO().save(s);
    }
    public void removeEvent(Schedule s, Event e) {
        s.removeEvent(e);
        scheduleDAO().save(s);
    }
    public void changeEvent(Schedule s, Event e) {
        s.getEvents().removeIf(scheduleEv -> scheduleEv.getId().equals(e.getId())
                && scheduleEv.getClass().equals(e.getClass())
        );
        scheduleDAO().save(s);
    }

    public Schedule getSchedule(String username) {
        var mgr = ServiceManager.getInstance();
        var user = mgr.getService(UserDAO.class).findByUsername(username);
        return user.map(User::getSchedule).orElse(null);
    }
}
