package edu.baylor.csi3471.netime_planner.models.persistence;

import edu.baylor.csi3471.netime_planner.models.domain_objects.User;

import java.util.Optional;

public class UserFileDAO extends FileDAO<User> {
    public UserFileDAO() {
        super(User.class);
    }

    public Optional<User> findByUsername(String username) {
        return getDataFromCache().values().parallelStream()
                .filter(u -> u.getName().equals(username))
                .findFirst();
    }
}
