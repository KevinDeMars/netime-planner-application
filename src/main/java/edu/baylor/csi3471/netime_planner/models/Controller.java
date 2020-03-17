package edu.baylor.csi3471.netime_planner.models;

import java.util.List;

public class Controller {
    private List<ControllerEventListener> listeners;
    private List<Event> events;
    private String username;
    private String email;
    private List<Group> groupsMemberOf;
    private List<Group> groupsOwned;

    // TODO
    //private getUserInformation(db);

    public List<Event> getEventsInInterval(DateTimeInterval interval) {
        throw new IllegalStateException("TODO"); // TODO
    }
    public void addEvent(Event event) {
        throw new IllegalStateException("TODO"); // TODO
    }
    public void removeEvent(Event event) {
        throw new IllegalStateException("TODO"); // TODO
    }
    public void changeEvent(Event oldValue, Event newValue) {
        throw new IllegalStateException("TODO"); // TODO
    }
    public void addEventListener(ControllerEventListener listener) {
        listeners.add(listener);
    }
    public void saveLocally() {
        throw new IllegalStateException("TODO"); // TODO
    }
    public void loadLocally() {
        throw new IllegalStateException("TODO"); // TODO
    }
    public void syncWithDatabase() {
        throw new IllegalStateException("TODO"); // TODO
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<Group> getGroupsMemberOf() {
        return groupsMemberOf;
    }

    public List<Group> getGroupsOwned() {
        return groupsOwned;
    }
}
