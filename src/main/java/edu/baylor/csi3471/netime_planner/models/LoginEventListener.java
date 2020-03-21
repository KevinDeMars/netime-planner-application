package edu.baylor.csi3471.netime_planner.models;

public interface LoginEventListener {
    void handleLogin(User user, boolean offlineMode);
}
