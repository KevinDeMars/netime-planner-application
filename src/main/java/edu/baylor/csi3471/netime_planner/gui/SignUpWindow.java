package edu.baylor.csi3471.netime_planner.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import edu.baylor.csi3471.netime_planner.models.LoginVerification;


public class SignUpWindow extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int FIELD_LENGTH = 20;

	private LoginVerification verifier;
	
	

	private JTextField usernameField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JPasswordField retypePasswordField = new JPasswordField();
	{
		usernameField.setColumns(FIELD_LENGTH);
		passwordField.setColumns(FIELD_LENGTH);
		retypePasswordField.setColumns(FIELD_LENGTH);
		
	}
	
	private JLabel passwordLabel = new JLabel("Password");
	private JLabel usernameLabel = new JLabel("Username");
	private JLabel retypePasswordLabel = new JLabel("Re-type Password");
	
	private JButton signUpButton = new JButton("Create account");
	{
		signUpButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (usernameField.getText().isEmpty() || String.copyValueOf(passwordField.getPassword()).isEmpty()) {
					JOptionPane.showMessageDialog(SignUpWindow.this, "All fields must be filled.");
					return;
				}
				
				if (!String.copyValueOf(passwordField.getPassword()).equals(String.copyValueOf(retypePasswordField.getPassword()))) {
					JOptionPane.showMessageDialog(SignUpWindow.this, "The passwords do not match.");
					return;
				}
				
				verifier.storeUsernameAndPassword(usernameField.getText(), passwordField.getPassword());
				
				SignUpWindow.this.setVisible(false);
			}
			
		});
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
		fieldPanel.add(Box.createRigidArea(new Dimension(0,5)));
		fieldPanel.add(retypePasswordLabel);
		fieldPanel.add(retypePasswordField);
		fieldPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	
	private JPanel buttonPanel = new JPanel();
	{
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
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
	
	public SignUpWindow() {
		this.setTitle("Create an Account");
		this.getContentPane().setBackground(Color.WHITE);
		this.setLayout(new GridBagLayout());
		this.add(panel, new GridBagConstraints());
		this.setMinimumSize(new Dimension(500, 500));
	}


	public LoginVerification getVerifier() {
		return verifier;
	}

	public void setVerifier(LoginVerification verifier) {
		this.verifier = verifier;
	}
	
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

	public JButton getSignUpButton() {
		return signUpButton;
	}

	public JLabel getRetypePasswordLabel() {
		return retypePasswordLabel;
	}

	public JPasswordField getRetypePasswordField() {
		return retypePasswordField;
	}


}
