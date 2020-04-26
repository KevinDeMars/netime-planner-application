package edu.baylor.csi3471;

import edu.baylor.csi3471.netime_planner.util.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilsTest {
    @Test
    public void testUsernameToDataFile()
    {
        testUsernameToDataFileWithParams("test", "data-test.xml");
        testUsernameToDataFileWithParams("Tomáš Černý", "data-Tom____ern_.xml");
        testUsernameToDataFileWithParams("../../../../secret-file.txt", "data-.._.._.._.._secret-file.txt.xml");
    }

    private void testUsernameToDataFileWithParams(String username, String expected)
    {
        assertEquals(expected, StringUtils.usernameToDataFile(username).getName());
    }
}
