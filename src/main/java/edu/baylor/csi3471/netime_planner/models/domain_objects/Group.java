package edu.baylor.csi3471.netime_planner.models.domain_objects;

import edu.baylor.csi3471.netime_planner.models.GroupPermission;

import java.util.Collection;

public class Group extends DomainObject {
    private String name;
    private User owner;
    private Collection<User> members;
    private GroupPermission permissions;

    private Collection<Schedule> sharedSchedules;
    private Collection<Event> sharedEvents;

    private Schedule schedule;
}
