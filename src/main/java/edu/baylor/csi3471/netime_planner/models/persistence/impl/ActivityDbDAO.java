package edu.baylor.csi3471.netime_planner.models.persistence.impl;

import edu.baylor.csi3471.netime_planner.models.TimeInterval;
import edu.baylor.csi3471.netime_planner.models.adapters.DayOfWeekAdapter;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Activity;
import edu.baylor.csi3471.netime_planner.models.persistence.ActivityDAO;
import edu.baylor.csi3471.netime_planner.models.persistence.DatabaseDAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActivityDbDAO extends DatabaseDAO<Activity> implements ActivityDAO {
    private static final Logger LOGGER = Logger.getLogger(ActivityDbDAO.class.getName());

    @Override
    public Optional<Activity> doFindById(int id) {
        try (var stmt = conn.prepareStatement("SELECT time_start, time_end, days_of_week, start_date, " +
                "end_date, week_interval, activity_name, description, location " +
                "FROM activities " +
                "WHERE activity_id = ?"))
        {
            stmt.setInt(1, id);
            var result = stmt.executeQuery();
            if (!result.next())
                return Optional.empty();

            var a = new Activity();
            a.setId(id);
            a.setTime(new TimeInterval(
                    result.getTime("time_start").toLocalTime(),
                    result.getTime("time_end").toLocalTime()
            ));
            a.setDays(DayOfWeekAdapter.toSet(result.getInt("days_of_week")));
            a.setStartDate(result.getDate("start_date").toLocalDate());
            a.setEndDate(result.getDate("end_date").toLocalDate());
            a.setWeekInterval(result.getObject("week_interval", Integer.class));
            a.setName(result.getString("activity_name"));
            a.setDescription(result.getString("description"));
            a.setLocation(result.getString("location"));
            return Optional.of(a);
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't get Activity by id", e);
        }

        return Optional.empty();
    }

    @Override
    public void doDelete(Activity obj) {
        try (var stmt = conn.prepareStatement("DELETE FROM activities " +
                "WHERE activity_id = ?"))
        {
            stmt.setInt(1, obj.getId());
            stmt.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't delete Activity", e);
        }
    }

    private void setSaveStatementVars(PreparedStatement stmt, Activity a) throws SQLException {
        stmt.setTime(1, Time.valueOf(a.getTime().getStart()));
        stmt.setTime(2, Time.valueOf(a.getTime().getEnd()));
        stmt.setInt(3, DayOfWeekAdapter.toInt(a.getDaysOfWeek()));
        stmt.setDate(4, Date.valueOf(a.getStartDate()));
        stmt.setDate(5, a.getEndDate().map(Date::valueOf).orElse(null));
        stmt.setObject(6, a.getWeekInterval().orElse(null));
        stmt.setString(7, a.getName());
        stmt.setString(8, a.getDescription().orElse(null));
        stmt.setString(9, a.getLocation().orElse(null));
    }

    @Override
    protected void doUpdate(Activity a) {
        try (var stmt = conn.prepareStatement("UPDATE activities " +
                "SET time_start = ?, time_end = ?, days_of_week = ?, " +
                "start_date = ?, end_date = ?, week_interval = ?, " +
                "activity_name = ?, description = ?, location = ? " +
                "WHERE activity_id = ?"))
        {
            setSaveStatementVars(stmt, a);
            stmt.setInt(10, a.getId());
            stmt.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't update Activity", e);
        }

    }

    @Override
    protected void doInsert(Activity a) {
        try (var stmt = conn.prepareStatement("INSERT INTO activities " +
                "(time_start, time_end, days_of_week, start_date, " +
                "end_date, week_interval, activity_name, description, location) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "RETURNING activity_id"))
        {
            setSaveStatementVars(stmt, a);
            var result = stmt.executeQuery();
            if (!result.next())
                LOGGER.log(Level.WARNING, "Couldn't insert Activity");
            else {
                int id = result.getInt(1);
                a.setId(id);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't insert Activity", e);
        }
    }

    @Override
    public int numSchedulesReferencedBy(Activity a) {
        try (var stmt = conn.prepareStatement("SELECT COUNT(activity_id) FROM schedules_activities WHERE activity_id = ?")) {
            stmt.setInt(1, a.getId());
            var result = stmt.executeQuery();
            result.next();
            return result.getInt(1);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't count referenced activities", e);
        }
        return 0;
    }
}
