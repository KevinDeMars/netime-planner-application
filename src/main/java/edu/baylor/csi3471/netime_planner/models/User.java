package edu.baylor.csi3471.netime_planner.models;

public class User {
    private int id;
    private String name;
    private String email;
    private char[] passwordHash;
    private Schedule schedule;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
