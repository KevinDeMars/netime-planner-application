package edu.baylor.csi3471.netime_planner.services;

public interface LoginVerificationService {
    public boolean login(String username, char[] password);
    public void register(String username, char[] password);
}
