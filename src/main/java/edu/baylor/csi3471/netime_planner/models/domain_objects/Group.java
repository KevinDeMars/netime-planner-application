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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Collection<User> getMembers() {
        return members;
    }

    public void setMembers(Collection<User> members) {
        this.members = members;
    }

    public GroupPermission getPermissions() {
        return permissions;
    }

    public void setPermissions(GroupPermission permissions) {
        this.permissions = permissions;
    }

    public Collection<Schedule> getSharedSchedules() {
        return sharedSchedules;
    }

    public void setSharedSchedules(Collection<Schedule> sharedSchedules) {
        this.sharedSchedules = sharedSchedules;
    }

    public Collection<Event> getSharedEvents() {
        return sharedEvents;
    }

    public void setSharedEvents(Collection<Event> sharedEvents) {
        this.sharedEvents = sharedEvents;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
