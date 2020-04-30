package edu.baylor.csi3471.netime_planner.models.persistence.impl;

import edu.baylor.csi3471.netime_planner.models.domain_objects.Activity;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Event;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Schedule;
import edu.baylor.csi3471.netime_planner.models.persistence.*;
import edu.baylor.csi3471.netime_planner.services.ServiceManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScheduleDbDAO extends DatabaseDAO<Schedule> implements ScheduleDAO {
    private static final Logger LOGGER = Logger.getLogger(ScheduleDbDAO.class.getName());

    @Override
    public Optional<Schedule> doFindById(int id) {
        try (var stmt = conn.prepareStatement("SELECT COUNT(schedule_id) FROM schedules WHERE schedule_id = ?")) {
            stmt.setInt(1, id);
            var result = stmt.executeQuery();
            result.next();
            if (result.getInt(1) == 0)
                return Optional.empty();

            var s = new Schedule();
            s.setId(id);

            try (var stmt2 = conn.prepareStatement("SELECT activity_id FROM schedules_activities WHERE schedule_id = ?")) {
                stmt2.setInt(1, id);
                var activityDAO = ServiceManager.getInstance().getService(ActivityDAO.class);
                result = stmt2.executeQuery();
                while (result.next()) {
                    int actId = result.getInt("activity_id");
                    s.addEvent(activityDAO.findById(actId).get());
                }
            }

            try (var stmt2 = conn.prepareStatement("SELECT deadline_id FROM schedules_deadlines WHERE schedule_id = ?")) {
                stmt2.setInt(1, id);
                var deadlineDAO = ServiceManager.getInstance().getService(DeadlineDAO.class);
                result = stmt2.executeQuery();
                while (result.next()) {
                    int deadlineId = result.getInt("deadline_id");
                    s.addEvent(deadlineDAO.findById(deadlineId).get());
                }
            }

            try (var stmt2 = conn.prepareStatement("SELECT activity_id FROM schedules_worktimes WHERE schedule_id = ?")) {
                stmt2.setInt(1, id);
                var activityDAO = ServiceManager.getInstance().getService(ActivityDAO.class);
                result = stmt2.executeQuery();
                while (result.next()) {
                    int actId = result.getInt("activity_id");
                    s.getWorkTimes().add(activityDAO.findById(actId).get());
                }
            }

            return Optional.of(s);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't load schedule", e);
        }
        return Optional.empty();
    }

    private void removeEvent(Schedule s, Event e) {
        if (e.getId() == null)
            return;
        var eventDao = ServiceManager.getInstance().getService(EventDAO.class);

        if (e instanceof Activity) {
            try (var stmt = conn.prepareStatement("DELETE FROM schedules_activities WHERE schedule_id = ? AND activity_id = ?")) {
                stmt.setInt(1, s.getId());
                stmt.setInt(2, e.getId());
                stmt.execute();
            } catch (SQLException ex) {
                LOGGER.log(Level.WARNING, "Couldn't delete from join table", ex);
            }
        }
        else {
            try (var stmt = conn.prepareStatement("DELETE FROM schedules_deadlines WHERE schedule_id = ? AND deadline_id = ?")) {
                stmt.setInt(1, s.getId());
                stmt.setInt(2, e.getId());
                stmt.execute();
            } catch (SQLException ex) {
                LOGGER.log(Level.WARNING, "Couldn't delete from join table", ex);
            }
        }

        if (eventDao.numSchedulesReferencedBy(e) == 0)
            eventDao.delete(e);
    }

    private void addEvent(Schedule s, Event e) {
        if (e instanceof Activity) {
            try (var stmt = conn.prepareStatement("INSERT INTO schedules_activities (schedule_id, activity_id) VALUES (?, ?)")) {
                stmt.setInt(1, s.getId());
                stmt.setInt(2, e.getId());
                stmt.execute();
            } catch (SQLException ex) {
                LOGGER.log(Level.WARNING, "Couldn't add to join table", ex);
            }
        }
        else {
            try (var stmt = conn.prepareStatement("INSERT INTO schedules_deadlines (schedule_id, deadline_id) VALUES (?, ?)")) {
                stmt.setInt(1, s.getId());
                stmt.setInt(2, e.getId());
                stmt.execute();
            } catch (SQLException ex) {
                LOGGER.log(Level.WARNING, "Couldn't add to join table", ex);
            }
        }
    }

    @Override
    public void doDelete(Schedule s) {
        for (var event : s.getEvents()) {
            removeEvent(s, event);
        }

        try (var stmt = conn.prepareStatement("DELETE FROM schedules_worktimes WHERE schedule_id = ?")) {
            stmt.setInt(1, s.getId());
            stmt.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error deleting from schedules_worktimes", e);
        }

        for (Activity a : s.getWorkTimes()) {
            try (var stmt = conn.prepareStatement("DELETE FROM activities WHERE activity_id = ?")) {
                stmt.setInt(1, a.getId());
                stmt.execute();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error deleting from activities", e);
            }
        }

        try (var stmt = conn.prepareStatement("DELETE FROM schedules WHERE schedule_id = ?")) {
            stmt.setInt(1, s.getId());
            stmt.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't delete schedule", e);
        }
    }

    @Override
    protected void doUpdate(Schedule s) {
        var eventDao = ServiceManager.getInstance().getService(EventDAO.class);
        for (var event : s.getEvents())
            eventDao.save(event);
        for (var wt : s.getWorkTimes())
            eventDao.save(wt);

        // Find out which events were removed by fetching from DB and getting set difference
        var inDb = doFindById(s.getId()).get();
        var removedEvents = new ArrayList<>(inDb.getEvents());
        removedEvents.removeAll(s.getEvents());
        for (var event : removedEvents) {
            removeEvent(s, event);
        }

        var addedEvents = new ArrayList<>(s.getEvents());
        addedEvents.removeAll(inDb.getEvents());
        for (var event : addedEvents) {
            addEvent(s, event);
        }

        var removedWorktimes = new ArrayList<>(inDb.getWorkTimes());
        removedWorktimes.removeAll(s.getWorkTimes());
        var activityDAO = ServiceManager.getInstance().getService(ActivityDAO.class);
        for (var wt : removedWorktimes) {
            activityDAO.delete(wt);
        }

        var addedWorktimes = new ArrayList<>(s.getWorkTimes());
        addedWorktimes.removeAll(inDb.getWorkTimes());
        for (var wt : addedWorktimes) {
            try (var stmt = conn.prepareStatement("INSERT INTO schedules_worktimes (schedule_id, activity_id) VALUES (?, ?)")) {
                stmt.setInt(1, s.getId());
                stmt.setInt(2, wt.getId());
                stmt.execute();
            } catch (SQLException ex) {
                LOGGER.log(Level.WARNING, "Couldn't add worktime", ex);
            }
        }
    }

    @Override
    protected void doInsert(Schedule obj) {
        try (var stmt = conn.prepareStatement("INSERT INTO schedules (schedule_id) VALUES (DEFAULT) RETURNING schedule_id")) {
            var result = stmt.executeQuery();
            result.next();
            obj.setId(result.getInt(1));
            doUpdate(obj);
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, "Couldn't insert into schedules", ex);
        }
    }
}
