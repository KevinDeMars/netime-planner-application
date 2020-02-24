package edu.baylor.csi3471.netime_planner.models;

import java.util.Collection;

public class Group {
    private int id;
    private String name;
    private User owner;
    private Collection<User> members;
    private GroupPermission permissions;

    private Collection<Schedule> sharedSchedules;
    private Collection<Event> sharedEvents;

    private Schedule schedule;
}
