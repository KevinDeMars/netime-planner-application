/*
 * Authors: Samuel Kim, Eric Jaroszewski, Kevin DeMars, Trenton Strickland, Joshua Kanagasabai
 * Class Title: Form
 * Class Description: This class represents a form where the user submits
 * information and which creates an object of a specified type based on the
 * information.
 */

package edu.baylor.csi3471.netime_planner.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public abstract class Form<T> extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JLabel[] labels;
	protected JFormattedTextField[] fields;
	protected JFrame frame = new JFrame();
	protected Component[] components = new Component[1];
	
	T createdValue = null;
	
	public T getCreatedValue() {
		return this.createdValue;
	}
	
	protected JButton cancelButton = new JButton("Cancel");
	{
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				
			}
			
		});
	}
	
	protected JButton submitButton = new JButton("Submit");
	{
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					createdValue = createValue();
				}
				catch(NumberFormatException exception) {
					Form.displayErrorMessage(Form.this, "Certain fields need to be filled.", "Empty Fields");
					return;
				}
				
				onFormSubmission.actionPerformed(e);
			}
			
		});
	}
	
	private ActionListener onFormSubmission;
	
	protected abstract T createValue();
	
	public static void displayErrorMessage(Component component, String message, String windowTitle) {
		JOptionPane.showOptionDialog(component, message, windowTitle, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, null, null);
	}
	
	public void setSubmissionListener(ActionListener onFormSubmission) {
		this.onFormSubmission = onFormSubmission;
	}
	
	public void createGUI() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = 1;
		constraints.gridheight = labels.length;
		constraints.fill = GridBagConstraints.VERTICAL;
		constraints.gridx = 0;

		//GridBagLayout layout = new GridBagLayout();
		
		JPanel labelPane = new JPanel(new GridLayout(labels.length,1));
		constraints.gridy = 0;
	    for (JLabel label : labels) {
	    	JPanel container = new JPanel(new BorderLayout());
	    	container.add(label, BorderLayout.CENTER);
	    	labelPane.add(container);
	    	//labelPane.add(label, constraints);
	    	constraints.gridy++;
	    }
	    
	    JPanel componentPane = new JPanel(new GridLayout(labels.length,1));
	    
	    constraints.gridy = 0;
	    for (Component component: components) {
	    	JPanel container = new JPanel(new BorderLayout());
	    	container.add(component);
	    	componentPane.add(container);
	    	//componentPane.add(component, constraints);
	    	constraints.gridy++;
	    }
	    
	    for (int i = 0; i < components.length; i++) {
	    	labels[i].setLabelFor(components[i]);
	    }
	    
	    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	    this.add(labelPane, BorderLayout.CENTER);
	    this.add(componentPane, BorderLayout.LINE_END);
	    
	    JMenuBar menu = new JMenuBar();
	    menu.add(submitButton);
	    menu.add(cancelButton);
	    
	    this.add(menu, BorderLayout.SOUTH);
	    
	    
	    frame.add(this);
	    frame.pack();
	    frame.setSize(frame.getWidth() + 100, frame.getHeight());
	    frame.setResizable(false);
		
	}
	
	public Form(String[] labelNames) {
		super(new BorderLayout());

		labels = new JLabel[labelNames.length];
		for (int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel(labelNames[i]);
		}
		
	 	
	}
	
	public Form(String[] labelNames, Format[] formats) {
		super(new BorderLayout());
		
		labels = new JLabel[labelNames.length];
		for (int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel(labelNames[i]);
		}
		
		fields = new JFormattedTextField[labels.length];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new JFormattedTextField(formats[i]);
		}
		
	 	JPanel labelPane = new JPanel(new GridLayout(0,1));
	    for (JLabel label : labels) {
	    	labelPane.add(label);
	    }
	    
	    JPanel fieldPane = new JPanel(new GridLayout(0,1));
	    for (JFormattedTextField field : fields) {
	    	field.setColumns(10);
	    	fieldPane.add(field);
	    }
	    
	    for (int i = 0; i < fields.length; i++) {
	    	labels[i].setLabelFor(fields[i]);
	    }
	    
	    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	    this.add(labelPane, BorderLayout.CENTER);
	    this.add(fieldPane, BorderLayout.LINE_END);
	    
	    JMenuBar menu = new JMenuBar();
	    menu.add(submitButton);
	    menu.add(cancelButton);
	    
	    this.add(menu, BorderLayout.SOUTH);
	    
	    
	    frame.add(this);
	    frame.pack();
	    frame.setSize(frame.getWidth() + 100, frame.getHeight());
	}
	
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
	
	public void setFieldText(String[] text) {
		for (int i = 0; i < fields.length; i++) {
			fields[i].setText(text[i]);
		}
	}

}
