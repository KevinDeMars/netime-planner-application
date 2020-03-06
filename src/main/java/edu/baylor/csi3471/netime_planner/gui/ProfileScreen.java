package edu.baylor.csi3471.netime_planner.gui;

import edu.baylor.csi3471.netime_planner.models.User;

public class ProfileScreen {
    // TODO

    // For now, use a dummy user for displaying the data
    private User user = makeDummyUser();

    private static User makeDummyUser() {
        var u = new User();
        u.setName("Billy Testman");
        u.setEmail("billy.testman@example.com");
        u.setId(1337);
        return u;
    }
}
