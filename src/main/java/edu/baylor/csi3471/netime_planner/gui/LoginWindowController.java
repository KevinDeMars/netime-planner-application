package edu.baylor.csi3471.netime_planner.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LoginWindowController {
	
	private LoginWindow loginWindow;
	
	public LoginWindowController(LoginWindow loginWindow) {
		this.loginWindow = loginWindow;
	}
	
	private boolean loginUser(String username, char[] password) {
		if (loginWindow.isOfflineMode()) {
			Scanner scanner = null;
			try {
				scanner = new Scanner(new File("Offline_Login_Information.txt"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayList<String> usernames = new ArrayList<String>(), passwords = new ArrayList<String>();
			while (scanner.hasNext()) {
				usernames.add(scanner.next());
				passwords.add(scanner.next());
			}
			scanner.close();
			
			String passwordString = String.copyValueOf(password);
			for (int i = 0; i < usernames.size(); i++) {
				if (username.equals(usernames.get(i)) && passwordString.equals(passwords.get(i))) {
					return true;
				}
			}
			return false;
		}
		
		return false;
		
	}
	
	private ActionListener onLogin = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String username = loginWindow.getUsernameField().getText();
			char[] password = loginWindow.getPasswordField().getPassword();
			if (loginUser(username, password)) {
				loginWindow.setVisible(false);
			}
			
		}
		
	};
	
	private ActionListener onSignUp = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};

	public ActionListener getOnLogin() {
		return onLogin;
	}

	public ActionListener getOnSignUp() {
		return onSignUp;
	}
	


}
