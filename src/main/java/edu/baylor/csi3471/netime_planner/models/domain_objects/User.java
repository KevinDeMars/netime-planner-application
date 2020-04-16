package edu.baylor.csi3471.netime_planner.models.domain_objects;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "user")
public class User extends DomainObject {
    private String name;
    private String email;
    private char[] passwordHash;
    private Schedule schedule = new Schedule();

    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char[] getPasswordHash() {
        return passwordHash;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<Group> getGroupsMemberOf() {
        throw new IllegalStateException(); // TODO
    }

    public List<Group> getGroupsOwned() {
        throw new IllegalStateException(); // TODO
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Arrays.equals(passwordHash, user.passwordHash) &&
                Objects.equals(schedule, user.schedule);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, email, schedule);
        result = 31 * result + Arrays.hashCode(passwordHash);
        return result;
    }
}
