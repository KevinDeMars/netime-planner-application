package edu.baylor.csi3471.netime_planner.gui;

import edu.baylor.csi3471.netime_planner.models.LoginEventListener;
import edu.baylor.csi3471.netime_planner.models.LoginVerification;
import edu.baylor.csi3471.netime_planner.models.LoginVerificationTestImplementation;
import edu.baylor.csi3471.netime_planner.models.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class LoginWindowController {
	
	private LoginWindow loginWindow;
	private List<LoginEventListener> loginEventListeners = new ArrayList<>();
	private LoginVerification verifier = new LoginVerificationTestImplementation();
	
	public LoginWindowController(LoginWindow loginWindow) {
		this.loginWindow = loginWindow;
	}
	
	private boolean loginUser(String username, char[] password) {
		return verifier.verifyUsernameAndPassword(username, password);
	}
	
	private ActionListener onLogin = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String username = loginWindow.getUsernameField().getText();
			char[] password = loginWindow.getPasswordField().getPassword();
			
			if (username.isEmpty() || String.copyValueOf(password).isEmpty()) {
				JOptionPane.showMessageDialog(loginWindow, "Both fields must be filled.");
				return;
			}
			
			
			if (loginUser(username, password)) {
				loginWindow.setVisible(false);
				var user = new User(); // TODO: Use Controller instead
				loginEventListeners.forEach(lis -> lis.handleLogin(user, loginWindow.isOfflineMode()));
			}
			else {
				JOptionPane.showMessageDialog(loginWindow, "There is no account that matches this username and password.");
			}
			
		}
		
	};

	public void addLoginEventListener(LoginEventListener lis) {
		loginEventListeners.add(lis);
	}
	
	private ActionListener onSignUp = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			SignUpWindow signUpWindow = new SignUpWindow();
			signUpWindow.setVerifier(verifier);
			signUpWindow.setVisible(true);
		}
		
	};

	public ActionListener getOnLogin() {
		return onLogin;
	}

	public ActionListener getOnSignUp() {
		return onSignUp;
	}
	


}
