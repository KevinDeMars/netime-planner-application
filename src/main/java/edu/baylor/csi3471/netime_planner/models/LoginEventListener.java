package edu.baylor.csi3471.netime_planner.models;

public interface LoginEventListener {
    void handleLogin(String username, boolean offlineMode);
}
