package edu.baylor.csi3471.netime_planner.models.persistence.impl;

import edu.baylor.csi3471.netime_planner.models.domain_objects.User;
import edu.baylor.csi3471.netime_planner.models.persistence.DatabaseDAO;
import edu.baylor.csi3471.netime_planner.models.persistence.ScheduleDAO;
import edu.baylor.csi3471.netime_planner.models.persistence.UserDAO;
import edu.baylor.csi3471.netime_planner.services.ServiceManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDbDAO extends DatabaseDAO<User> implements UserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDbDAO.class.getName());

    @Override
    public Optional<User> doFindById(int id) {
        try (var stmt = conn.prepareStatement("SELECT username, email, schedule_id FROM users WHERE user_id = ?")){
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                var u = new User();
                u.setId(id);
                u.setName(result.getString("username"));
                u.setEmail(result.getString("email"));
                int scheduleId = result.getInt("schedule_id");
                var scheduleDAO = ServiceManager.getInstance().getService(ScheduleDAO.class);
                var schedule = scheduleDAO.findById(scheduleId);
                if (!schedule.isPresent()) {
                    LOGGER.log(Level.WARNING, "Couldn't load user " + u.getName() + "'s schedule");
                    return Optional.empty();
                }
                u.setSchedule(schedule.get());
                return Optional.of(u);
            }
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't find user by id: ", e);
        }

        return Optional.empty();
    }

    @Override
    public void doDelete(User obj) {
        try (var stmt = conn.prepareStatement("DELETE FROM users WHERE user_id = ?")) {
            stmt.setInt(1, obj.getId());
            stmt.execute();
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't delete user: ", e);
        }

        var scheduleDao = ServiceManager.getInstance().getService(ScheduleDAO.class);
        scheduleDao.delete(obj.getSchedule());
    }

    @Override
    protected void doUpdate(User obj) {
        try (var stmt = conn.prepareStatement("UPDATE users SET username = ?, email = ? WHERE user_id = ?");) {
            stmt.setString(1, obj.getName());
            stmt.setString(2, obj.getEmail());
            stmt.setInt(3, obj.getId());
            stmt.execute();

            if (obj.getPasswordHash() != null) {
                try (var stmt2 = conn.prepareStatement("UPDATE users SET password_hash = ? WHERE user_id = ?")) {
                    stmt2.setString(1, new String(obj.getPasswordHash()));
                    stmt2.setInt(2, obj.getId());
                    stmt2.execute();
                }
            }
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't update user: ", e);
        }
        var scheduleDAO = ServiceManager.getInstance().getService(ScheduleDAO.class);
        scheduleDAO.save(obj.getSchedule());
    }

    @Override
    protected void doInsert(User u) {
        var scheduleDAO = ServiceManager.getInstance().getService(ScheduleDAO.class);
        scheduleDAO.save(u.getSchedule());

        try (var stmt = conn.prepareStatement("INSERT INTO users (username, email, password_hash, schedule_id) VALUES (?, ?, ?, ?) RETURNING user_id")){
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, new String(u.getPasswordHash()));
            stmt.setInt(4, u.getSchedule().getId());
            var result = stmt.executeQuery();
            result.next();
            int id = result.getInt(1);
            u.setId(id);
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't update user: ", e);
        }
    }

    @Override
    public void loadPasswordHash(User u) {
        try (var stmt = conn.prepareStatement("SELECT password_hash FROM users WHERE user_id = ?")) {
            stmt.setInt(1, u.getId());
            var result = stmt.executeQuery();
            if (result.next()) {
                u.setPasswordHash(result.getString(1).toCharArray());
            }
            else {
                LOGGER.log(Level.WARNING, "Couldn't find user " + u.getName() + " with id of " + u.getId());
            }
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't load password hash: ", e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (var stmt = conn.prepareStatement("SELECT user_id FROM users WHERE username = ?")) {
            stmt.setString(1, username);
            var result = stmt.executeQuery();
            if (!result.next())
                return Optional.empty();
            return findById(result.getInt("user_id"));
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't find user by name ", e);
        }
        return Optional.empty();
    }
}
