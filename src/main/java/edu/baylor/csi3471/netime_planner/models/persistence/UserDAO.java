package edu.baylor.csi3471.netime_planner.models.persistence;

import edu.baylor.csi3471.netime_planner.models.domain_objects.User;

import java.util.Optional;

public interface UserDAO extends DAO<User> {
    void loadPasswordHash(User u);
    Optional<User> findByUsername(String username);
}
