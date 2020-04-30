/*
 * Authors: Samuel Kim, Eric Jaroszewski, Kevin DeMars, Trenton Strickland, Joshua Kanagasabai
 * Class Title: Form
 * Class Description: This class represents a form where the user submits
 * information and which creates an object of a specified type based on the
 * information.
 */

package edu.baylor.csi3471.netime_planner.gui.form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;


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
		cancelButton.addActionListener(e -> frame.setVisible(false));
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

				if (onFormSubmission != null)
					onFormSubmission.actionPerformed(e);
			}
			
		});
	}
	
	private ActionListener onFormSubmission;
	
	protected abstract T createValue();
	
	public static void displayErrorMessage(Component component, String message, String windowTitle) {
		JOptionPane.showOptionDialog(component, message, windowTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
	}
	
	public void setSubmissionListener(ActionListener onFormSubmission) {
		this.onFormSubmission = onFormSubmission;
	}
	
	public void createGUI() {
		JPanel labelPane = new JPanel(new GridLayout(labels.length,1));
	    for (JLabel label : labels) {
	    	JPanel container = new JPanel(new BorderLayout());
	    	container.add(label, BorderLayout.CENTER);
	    	labelPane.add(container);
	    	//labelPane.add(label, constraints);
	    }
	    
	    JPanel componentPane = new JPanel(new GridLayout(labels.length,1));
	    
	    for (Component component: components) {
	    	JPanel container = new JPanel(new BorderLayout());
	    	container.add(component);
	    	componentPane.add(container);
	    	//componentPane.add(component, constraints);
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
	    componentPane.setPreferredSize(componentPane.getSize());
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
