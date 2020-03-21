package edu.baylor.csi3471.netime_planner.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class LoginVerificationTestImplementation implements LoginVerification{

	@Override
	public boolean verifyUsernameAndPassword(String username, char[] password) {
		if (username.contentEquals("Admin")) {
			return true;
		}
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("Offline_Login_Information.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Login information not found.");
			e.printStackTrace();
		}
		
		ArrayList<String> usernames = new ArrayList<String>();
		ArrayList<String> hashes = new ArrayList<String>();
		
		while (scanner.hasNext()) {
			usernames.add(scanner.next());
			hashes.add(scanner.next());
		}
		scanner.close();
		
		String passwordString = String.copyValueOf(password);
		for (int i = 0; i < usernames.size(); i++) {
			if (username.equals(usernames.get(i))) {
				if (BCrypt.checkpw(passwordString, hashes.get(i))) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void storeUsernameAndPassword(String username, char[] password) {
		try {
			FileWriter writer = new FileWriter("Offline_Login_Information.txt", true);
			writer.write(username + "\n");
			String hash = BCrypt.hashpw(String.copyValueOf(password), BCrypt.gensalt());
			writer.write(hash + "\n");
			
			writer.close();
			
		} catch (IOException e1) {
			System.out.println("Login file not found.");
			e1.printStackTrace();
		}
		
	}

}
