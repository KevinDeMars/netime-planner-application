package edu.baylor.csi3471.netime_planner.gui.controllers;

import edu.baylor.csi3471.netime_planner.models.GroupPermission;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Group;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Schedule;
import edu.baylor.csi3471.netime_planner.models.domain_objects.User;
import edu.baylor.csi3471.netime_planner.services.GroupService;
import edu.baylor.csi3471.netime_planner.services.ServiceManager;

public class GroupScreenController {

    private final GroupService groupSvc = ServiceManager.getInstance().getService(GroupService.class);
    private final Group group;

    public GroupScreenController(Group g){group = g;}

    public String getName(){return group.getName();}

    public void addMember(User u){group.getMembers().add(u);}

    public void setSchedule(Schedule s){group.setSchedule(s);}

    public void removeMember(User u){
        group.getMembers().remove(u);
    }

    public void setPermissions(GroupPermission grpP){group.setPermissions(grpP);}
}
