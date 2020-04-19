package edu.baylor.csi3471.netime_planner.models.persistence;

import edu.baylor.csi3471.netime_planner.models.domain_objects.User;
import edu.baylor.csi3471.netime_planner.services.ServiceManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDbDAO extends DatabaseDAO<User> {
    private static final Logger LOGGER = Logger.getLogger(UserDbDAO.class.getName());
    private final Connection conn = ServiceManager.getInstance().getService(Connection.class);

    @Override
    public Optional<User> findById(int id) {
        try (var stmt = conn.prepareStatement("SELECT name, email FROM users WHERE id = ?")){
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                var u = new User();
                u.setId(id);
                u.setName(result.getString("name"));
                u.setEmail(result.getString("email"));
                return Optional.of(u);
            }
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't find user by id: ", e);
        }

        return Optional.empty();
    }

    @Override
    public void delete(User obj) {
        try (var stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
            stmt.setInt(1, obj.getId());
            stmt.execute();
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't delete user: ", e);
        }
    }

    @Override
    protected void doUpdate(User obj) {
        try (var stmt = conn.prepareStatement("UPDATE users SET name = ?, email = ? WHERE id = ?");) {
            stmt.setString(1, obj.getName());
            stmt.setString(2, obj.getEmail());
            stmt.setInt(3, obj.getId());
            stmt.execute();

            if (obj.getPasswordHash() != null) {
                try (var stmt2 = conn.prepareStatement("UPDATE users SET password_hash = ? WHERE id = ?")) {
                    stmt2.setString(1, new String(obj.getPasswordHash()));
                    stmt2.setInt(2, obj.getId());
                    stmt2.execute();
                }
            }
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't update user: ", e);
        }
    }

    @Override
    protected void doInsert(User obj) {
        try (var stmt = conn.prepareStatement("INSERT INTO users (name, email, password_hash) VALUES (?, ?, ?) RETURNING id")){
            stmt.setString(1, obj.getName());
            stmt.setString(2, obj.getEmail());
            stmt.setString(3, new String(obj.getPasswordHash()));
            var result = stmt.executeQuery();
            result.next();
            int id = result.getInt(1);
            obj.setId(id);
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Couldn't update user: ", e);
        }
    }

    void loadPasswordHash(User u) {
        try (var stmt = conn.prepareStatement("SELECT password_hash FROM users WHERE id = ?")) {
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
}
