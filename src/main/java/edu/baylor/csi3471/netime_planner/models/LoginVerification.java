package edu.baylor.csi3471.netime_planner.models;

public interface LoginVerification {
	
	boolean verifyUsernameAndPassword(String username, char[] password);
	
	void storeUsernameAndPassword(String username, char[] password);

}
