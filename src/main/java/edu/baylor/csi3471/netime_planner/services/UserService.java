package edu.baylor.csi3471.netime_planner.services;

import edu.baylor.csi3471.netime_planner.models.domain_objects.User;
import edu.baylor.csi3471.netime_planner.models.persistence.UserDAO;

import java.util.Optional;

public class UserService {
    public void changeEmail(String newEmail) {
        throw new UnsupportedOperationException("Unimplemented"); // TODO
    }
    public Optional<User> getUserInfo(String username) {
        var dao = ServiceManager.getInstance().getService(UserDAO.class);
        return dao.findByUsername(username);
    }
}
