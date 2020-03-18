package edu.baylor.csi3471.netime_planner.models;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    protected List<ControllerEventListener> listeners = new ArrayList<>();
    protected User user;

    // private getUserInformation(db); // TODO

    public Schedule getSchedule() {
        return user.getSchedule();
    }

    // Similar to schedule.makeTodoList but that only deals with deadlines and this also deals with activities
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
        saveLocally(new File("data.xml"));
    }

    protected void saveLocally(File f) {
        try {
            var ctx = JAXBContext.newInstance(User.class, Deadline.class, Activity.class);
            var marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(user, f);
        } catch (JAXBException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not save " + f.getName(), "Error saving", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadLocally() {
        loadLocally(new File("data.xml"));
    }
    protected void loadLocally(File f) {
        try {
            var ctx = JAXBContext.newInstance(User.class, Deadline.class, Activity.class);
            var unmarshaller = ctx.createUnmarshaller();
            this.user = (User) unmarshaller.unmarshal(f);
        } catch (JAXBException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not load " + f.getName(), "Error loading", JOptionPane.ERROR_MESSAGE);
            this.user = new User(); // TODO: What to do here?
        }
    }
    public void syncWithDatabase() {
        throw new IllegalStateException("TODO"); // TODO
    }

    public User getUser() {
        return user;
    }
}
