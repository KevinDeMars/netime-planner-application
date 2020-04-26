package edu.baylor.csi3471.netime_planner.gui.screens;

import edu.baylor.csi3471.netime_planner.gui.controllers.GroupScreenController;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Group;

public class GroupScreen {

    private final GroupScreenController controller;

    public GroupScreen(Group g){
        controller = new GroupScreenController(g);
    }


}
