package edu.baylor.csi3471.netime_planner.models.persistence.impl;

import edu.baylor.csi3471.netime_planner.models.domain_objects.Deadline;
import edu.baylor.csi3471.netime_planner.models.domain_objects.DomainObject;
import edu.baylor.csi3471.netime_planner.models.persistence.ActivityDAO;
import edu.baylor.csi3471.netime_planner.models.persistence.DatabaseDAO;
import edu.baylor.csi3471.netime_planner.models.persistence.DeadlineDAO;
import edu.baylor.csi3471.netime_planner.services.ServiceManager;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeadlineDbDAO extends DatabaseDAO<Deadline> implements DeadlineDAO {
    private static final Logger LOGGER = Logger.getLogger(DeadlineDbDAO.class.getName());

    @Override
    protected Optional<Deadline> doFindById(int id) {
        try (var stmt = conn.prepareStatement("SELECT due_timestamp, start_timestamp, deadline_name, description, location, category_activity_id FROM deadlines WHERE deadline_id = ?")) {
            stmt.setInt(1, id);
            var result = stmt.executeQuery();
            if (!result.next())
                return Optional.empty();
            var d = new Deadline();
            d.setId(id);
            d.setDueDatetime(result.getTimestamp("due_timestamp").toLocalDateTime());
            var startTS = result.getTimestamp("start_timestamp");
            if (startTS != null)
                d.setStartDatetime(startTS.toLocalDateTime());
            d.setName(result.getString("deadline_name"));
            d.setDescription(result.getString("description"));
            d.setLocation(result.getString("location"));
            Integer catId = result.getObject("category_activity_id", Integer.class);
            if (catId != null) {
                var activityDao = ServiceManager.getInstance().getService(ActivityDAO.class);
                var optCategory = activityDao.findById(catId);
                if (!optCategory.isPresent()) {
                    LOGGER.log(Level.WARNING, "Category ID was present, but couldn't get the category");
                }
                else {
                    d.setCategory(optCategory.get());
                }
            }

            return Optional.of(d);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't find Deadline by ID", e);
            return Optional.empty();
        }
    }

    @Override
    public void doDelete(Deadline obj) {
        try (var stmt = conn.prepareStatement("DELETE FROM deadlines WHERE deadline_id = ?")) {
            stmt.setInt(1, obj.getId());
            stmt.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't delete Deadline", e);
        }
    }

    @Override
    protected void doUpdate(Deadline d) {
        try (var stmt = conn.prepareStatement("UPDATE deadlines SET due_timestamp = ?, start_timestamp = ?, deadline_name = ?, " +
                "description = ?, location = ?, category_activity_id = ?" +
                "WHERE deadline_id = ?"))
        {
            stmt.setTimestamp(1, Timestamp.valueOf(d.getDueDatetime()));
            stmt.setTimestamp(2, d.getStartDatetime().map(Timestamp::valueOf).orElse(null));
            stmt.setString(3, d.getName());
            stmt.setString(4, d.getDescription().orElse(null));
            stmt.setString(5, d.getLocation().orElse(null));
            stmt.setObject(6, d.getCategory().map(DomainObject::getId).orElse(null));
            stmt.setInt(7, d.getId());
            stmt.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't update Deadline", e);
        }

    }

    @Override
    protected void doInsert(Deadline d) {
        try (var stmt = conn.prepareStatement("INSERT INTO deadlines (due_timestamp, start_timestamp, deadline_name, description, location, category_activity_id)" +
                "VALUES(?, ?, ?, ?, ?, ?) RETURNING deadline_id"))
        {
            stmt.setTimestamp(1, Timestamp.valueOf(d.getDueDatetime()));
            stmt.setTimestamp(2, d.getStartDatetime().map(Timestamp::valueOf).orElse(null));
            stmt.setString(3, d.getName());
            stmt.setString(4, d.getDescription().orElse(null));
            stmt.setString(5, d.getLocation().orElse(null));
            stmt.setObject(6, d.getCategory().map(DomainObject::getId).orElse(null));
            var result = stmt.executeQuery();
            if (!result.next())
                LOGGER.log(Level.WARNING, "Couldn't insert Deadline");
            else {
                int id = result.getInt(1);
                d.setId(id);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't insert Deadline", e);
        }
    }

    @Override
    public int numSchedulesReferencedBy(Deadline d) {
        try (var stmt = conn.prepareStatement("SELECT COUNT(schedule_id) FROM schedules_deadlines WHERE deadline_id = ?")) {
            stmt.setInt(1, d.getId());
            var result = stmt.executeQuery();
            result.next();
            return result.getInt(1);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't count referenced schedules", e);
        }
        return 0;
    }
}
