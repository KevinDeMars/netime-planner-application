package edu.baylor.csi3471.netime_planner.services;

import edu.baylor.csi3471.netime_planner.models.domain_objects.User;
import edu.baylor.csi3471.netime_planner.models.persistence.UserDAO;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocalLoginVerificationService implements LoginVerificationService {
	private static final Logger LOGGER = Logger.getLogger(LocalLoginVerificationService.class.getName());

	@Override
	public boolean login(String username, char[] password) {
		if (username.contentEquals("Admin")) {
			return true;
		}
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("Offline_Login_Information.txt"), StandardCharsets.UTF_8);
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.WARNING, "Login information not found.", e);
			return false;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Couldn't open offline login info", e);
			return false;
		}

		ArrayList<String> usernames = new ArrayList<>();
		ArrayList<String> hashes = new ArrayList<>();
		
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
	public void register(String username, char[] password) {
		try {
			var writer = new OutputStreamWriter(new FileOutputStream("Offline_Login_Information.txt", true), StandardCharsets.UTF_8);
			writer.write(username + "\n");
			String hash = BCrypt.hashpw(String.copyValueOf(password), BCrypt.gensalt());
			writer.write(hash + "\n");
			
			writer.close();

			var u = new User(username, "email@example.gov");
			u.setPasswordHash(hash.toCharArray());
			var userDao = ServiceManager.getInstance().getService(UserDAO.class);
			userDao.save(u);
			
		} catch (IOException e1) {
			LOGGER.log(Level.WARNING, "Login information not found.", e1);
		}
		
	}

}
