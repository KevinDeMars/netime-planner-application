package edu.baylor.csi3471.netime_planner.models;

import edu.baylor.csi3471.netime_planner.util.StringUtils;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    protected List<ControllerEventListener> listeners = new ArrayList<>();
    protected int maxSize;
    protected User user;

    // private getUserInformation(db); // TODO

    public void init(String username, boolean offline) {
        System.out.println("Controller.init");
        if (offline) {
            if (!loadLocally(StringUtils.usernameToDataFile(username))) {
                System.out.println("Failed to load data for " + username);
                user = new User(username, "email@example.gov");
            }
        }
        else {
            throw new IllegalStateException("Online init not implemented"); // TODO
        }
    }

    public void setMaxSize(int maxSize){
        this.maxSize = maxSize;
    }
    public int getMaxSize(){
        return maxSize;
    }

    public Schedule getSchedule() {
        return user.getSchedule();
    }

    // Similar to schedule.makeTodoList but that only deals with deadlines and this also deals with activities
    public List<Event> getEventsInInterval(DateTimeInterval interval) {
        throw new IllegalStateException("TODO"); // TODO
    }

    public List<Event> getEvents() {
        return (List<Event>) user.getSchedule().getEvents();
    }
    public void addEvent(Event event) {
        user.getSchedule().addEvent(event);
    }
    public void removeEvent(Event event) {
        user.getSchedule().removeEvent(event);
    }

    public void changeEvent(Event oldValue, Event newValue) {
        user.getSchedule().removeEvent(oldValue);
        user.getSchedule().addEvent(newValue);
    }
    public void addEventListener(ControllerEventListener listener) {
        listeners.add(listener);
    }

    public boolean saveLocally() {
        return saveLocally(StringUtils.usernameToDataFile(user.getName()));
    }

    protected boolean saveLocally(File f) {
        try {
            var ctx = JAXBContext.newInstance(User.class, Deadline.class, Activity.class);
            var marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(user, f);
            System.out.println("Saved");
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loadLocally() {
        return loadLocally(StringUtils.usernameToDataFile(user.getName()));
    }

    protected boolean loadLocally(File f) {
        if (!f.exists())
            return false;
        try {
            var ctx = JAXBContext.newInstance(User.class, Deadline.class, Activity.class);
            var unmarshaller = ctx.createUnmarshaller();
            this.user = (User) unmarshaller.unmarshal(f);
            System.out.println("Loaded data for " + user.getName());
        } catch (JAXBException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not load " + f.getName(), "Error loading", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    public void syncWithDatabase() {
        throw new IllegalStateException("TODO"); // TODO
    }

    public User getUser() {
        return user;
    }
}
