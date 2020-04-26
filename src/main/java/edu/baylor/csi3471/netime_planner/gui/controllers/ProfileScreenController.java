package edu.baylor.csi3471.netime_planner.gui.controllers;

import edu.baylor.csi3471.netime_planner.models.domain_objects.Activity;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Deadline;
import edu.baylor.csi3471.netime_planner.models.domain_objects.User;
import edu.baylor.csi3471.netime_planner.services.ServiceManager;
import edu.baylor.csi3471.netime_planner.services.UserService;

import java.util.List;

public class ProfileScreenController {
    private final UserService userSvc = ServiceManager.getInstance().getService(UserService.class);
    private final User user;

    public ProfileScreenController(String username) {
        user = userSvc.getUserInfo(username).orElseThrow(() -> new IllegalArgumentException("User " + username + " not found"));
    }

    public String getUsername() {
        return user.getName();
    }
    public String getEmail() {
        return user.getEmail();
    }

    public void addUpcomingDeadline(Deadline d) {
        throw new UnsupportedOperationException("Unimplemented"); // TODO
    }
    public List<Deadline> getUpcomingDeadlines() {
        throw new UnsupportedOperationException("Unimplemented"); // TODO
    }
    public List<Activity> getCategories() {
        throw new UnsupportedOperationException("Unimplemented"); // TODO
    }
    public void addCategory(Activity a) {
        throw new UnsupportedOperationException("Unimplemented"); // TODO
    }
}
