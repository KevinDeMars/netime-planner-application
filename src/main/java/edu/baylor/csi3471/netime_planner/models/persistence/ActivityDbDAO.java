package edu.baylor.csi3471.netime_planner.models.persistence;

import edu.baylor.csi3471.netime_planner.models.domain_objects.Activity;
import edu.baylor.csi3471.netime_planner.services.ServiceManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActivityDbDAO extends DatabaseDAO<Activity> {
    private static final Logger LOGGER = Logger.getLogger(ActivityDbDAO.class.getName());
    private final Connection conn = ServiceManager.getInstance().getService(Connection.class);

    @Override
    public Optional<Activity> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void delete(Activity obj) {

    }

    @Override
    protected void doUpdate(Activity obj) {

    }

    @Override
    protected void doInsert(Activity obj) {

    }

    int numSchedulesReferencedBy(Activity a) {
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
