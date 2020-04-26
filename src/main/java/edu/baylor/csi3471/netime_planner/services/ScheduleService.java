package edu.baylor.csi3471.netime_planner.services;

import edu.baylor.csi3471.netime_planner.models.DateTimeInterval;
import edu.baylor.csi3471.netime_planner.models.ScheduleEventListener;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Activity;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Event;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Schedule;
import edu.baylor.csi3471.netime_planner.models.domain_objects.User;
import edu.baylor.csi3471.netime_planner.models.persistence.ScheduleDAO;
import edu.baylor.csi3471.netime_planner.models.persistence.UserDAO;

import java.time.Duration;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.TemporalUnit;
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

    public void addWorktime(Schedule s, Activity workTime) {
        s.getWorkTimes().add(workTime);
        scheduleDAO().save(s);
    }

    public void removeWorktime(Schedule s, Activity workTime) {
        s.getWorkTimes().remove(workTime);
        scheduleDAO().save(s);
    }

    public long getFreeTime(Schedule s, DateTimeInterval d){


        long hours = Duration.between(d.getStart(), d.getEnd()).toHours();
        for (Activity a : s.getWorkTimes()){
            if(a.getEndDate().isPresent()) {
                if (d.contains(a.getStartDate().atTime(d.getStart().toLocalTime())) ||
                        d.contains(a.getEndDate().get().atTime(d.getEnd().toLocalTime()))) {
                    hours -= Duration.between(a.getStartDate(), a.getEndDate().get()).toHours();
                    hours -= Duration.between(a.getTime().getStart(), a.getTime().getEnd()).toHours();

                    //If the activity starts before the DateTimeInterval's starting point,
                    //add back the excess time removed
                    if (a.getStartDate().isBefore(ChronoLocalDate.from(d.getStart())) ||
                            (a.getStartDate().equals(ChronoLocalDate.from(d.getStart()))
                                    && a.getTime().getStart().isBefore(d.getStart().toLocalTime()))) {
                        hours += Duration.between(a.getStartDate(), d.getStart().toLocalDate()).toHours();
                        hours += Duration.between(a.getTime().getStart(), d.getStart().toLocalTime()).toHours();
                    }

                    //If the activity ends after the DateTimeInterval's end point,
                    //add back the excess time removed
                    if (a.getEndDate().get().isAfter(ChronoLocalDate.from(d.getEnd())) ||
                            (a.getEndDate().get().equals(ChronoLocalDate.from(d.getEnd()))
                                    && a.getTime().getEnd().isAfter(d.getEnd().toLocalTime()))) {
                        hours += Duration.between(d.getEnd().toLocalDate(), a.getEndDate().get()).toHours();
                        hours += Duration.between(d.getEnd().toLocalTime(), a.getTime().getEnd()).toHours();
                    }
                }
            }
        }
        if(hours < 0)
            hours = 0;
        return hours;
    }

    public void listenToChanges(ScheduleEventListener l) {
        listeners.add(l);
    }
}
