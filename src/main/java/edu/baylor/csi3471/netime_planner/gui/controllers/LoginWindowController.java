package edu.baylor.csi3471.netime_planner.gui.controllers;

import edu.baylor.csi3471.netime_planner.gui.LoginEventListener;
import edu.baylor.csi3471.netime_planner.gui.LoginWindow;
import edu.baylor.csi3471.netime_planner.gui.SignUpWindow;
import edu.baylor.csi3471.netime_planner.services.LoginVerificationService;
import edu.baylor.csi3471.netime_planner.services.ServiceManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LoginWindowController {
	
	private final LoginWindow loginWindow;
	private final List<LoginEventListener> loginEventListeners = new ArrayList<>();
	private final LoginVerificationService verifier = ServiceManager.getInstance().getService(LoginVerificationService.class);
	
	public LoginWindowController(LoginWindow loginWindow) {
		this.loginWindow = loginWindow;
	}
	
	private boolean loginUser(String username, char[] password) {
		return verifier.login(username, password);
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
				loginEventListeners.forEach(lis -> lis.handleLogin(username));
			}
			else {
				JOptionPane.showMessageDialog(loginWindow, "There is no account that matches this username and password.");
			}
			
		}
		
	};

	public void addLoginEventListener(LoginEventListener lis) {
		loginEventListeners.add(lis);
	}
	
	private ActionListener onSignUp = e -> {
		SignUpWindow signUpWindow = new SignUpWindow();
		signUpWindow.setVerifier(verifier);
		signUpWindow.setVisible(true);
	};

	public ActionListener getOnLogin() {
		return onLogin;
	}

	public ActionListener getOnSignUp() {
		return onSignUp;
	}
	


}
