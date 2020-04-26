package edu.baylor.csi3471.netime_planner.gui.windows;

import edu.baylor.csi3471.netime_planner.services.LoginVerificationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpWindow extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int FIELD_LENGTH = 20;

	private LoginVerificationService verifier;

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
					JOptionPane.showMessageDialog(SignUpWindow.this, "Both fields must be filled.");
					return;
				}
				
				if (!String.copyValueOf(passwordField.getPassword()).equals(String.copyValueOf(retypePasswordField.getPassword()))) {
					JOptionPane.showMessageDialog(SignUpWindow.this, "The passwords do not match.");
					return;
				}
				
				verifier.register(usernameField.getText(), passwordField.getPassword());
				
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

	public LoginVerificationService getVerifier() {
		return verifier;
	}

	public void setVerifier(LoginVerificationService verifier) {
		this.verifier = verifier;
	}
}
