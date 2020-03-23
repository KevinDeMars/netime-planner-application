package edu.baylor.csi3471.netime_planner.util;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class StringUtils {
    public static File usernameToDataFile(String username) {
        // This isn't perfect because the regex will remove valid characters like é
        // so I made it only apply the regex if the filename is invalid.
        try {
            return Paths.get("data-" + username + ".xml").toFile();
        }
        catch (InvalidPathException e) {
            String censoredUsername = username.replaceAll("[^a-zA-Z0-9.\\-]", "_");
            return Paths.get("data-" + censoredUsername + ".xml").toFile();
        }
    }
}
