package edu.baylor.csi3471.netime_planner.models;

public interface LoginVerification {
	
	public boolean verifyUsernameAndPassword(String username, char[] password);
	
	public void storeUsernameAndPassword(String username, char[] password);

}
