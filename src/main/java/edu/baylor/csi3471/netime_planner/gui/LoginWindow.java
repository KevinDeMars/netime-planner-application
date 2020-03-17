package edu.baylor.csi3471.netime_planner.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean offlineMode = true;

	private static final int FIELD_LENGTH = 20;
	
	private LoginWindowController controller = new LoginWindowController(this);
	
	public JTextField getUsernameField() {
		return usernameField;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public JLabel getPasswordLabel() {
		return passwordLabel;
	}

	public JLabel getUsernameLabel() {
		return usernameLabel;
	}

	public JButton getLoginButton() {
		return loginButton;
	}

	public JButton getSignUpButton() {
		return signUpButton;
	}

	private JTextField usernameField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	{
		usernameField.setColumns(FIELD_LENGTH);
		passwordField.setColumns(FIELD_LENGTH);
	}
	
	private JLabel passwordLabel = new JLabel("Password");
	private JLabel usernameLabel = new JLabel("Username");
	
	private JButton loginButton = new JButton("Login");
	{
		loginButton.addActionListener(controller.getOnLogin());
	}
	
	
	private JButton signUpButton = new JButton("Sign Up");
	{
		signUpButton.addActionListener(controller.getOnSignUp());
	}
	
	
	private JPanel fieldPanel = new JPanel();
	{
		fieldPanel.setBackground(Color.WHITE);
		fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
		fieldPanel.add(usernameLabel);
		fieldPanel.add(usernameField);
		fieldPanel.add(Box.createRigidArea(new Dimension(0,5)));
		fieldPanel.add(passwordLabel);
		fieldPanel.add(passwordField);
		fieldPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	
	private JPanel buttonPanel = new JPanel();
	{
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(loginButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(0,5)));
		buttonPanel.add(signUpButton);
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	
	private JPanel panel = new JPanel();
	{
		panel.setBackground(Color.WHITE);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(fieldPanel);
		panel.add(Box.createRigidArea(new Dimension(0,10)));
		panel.add(buttonPanel);
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	
	public LoginWindow() {
		this.setTitle("NETime Planner");
		this.getContentPane().setBackground(Color.WHITE);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		this.add(panel, new GridBagConstraints());
		this.setMinimumSize(new Dimension(500, 500));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public boolean isOfflineMode() {
		return offlineMode;
	}

	public void setOfflineMode(boolean offlineMode) {
		this.offlineMode = offlineMode;
	}
	
}
