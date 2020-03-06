package edu.baylor.csi3471.netime_planner.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUITester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CreateDeadlineForm d = new CreateDeadlineForm();
		d.setVisible(true);
		d.setSubmissionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Submitted");
				
			}
			
		});
		
		CreateActivityForm a = new CreateActivityForm();
		a.setVisible(true);
		a.setSubmissionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Submitted");
			}
			
		});
	}

}
