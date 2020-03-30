import edu.baylor.csi3471.netime_planner.models.Controller;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlTest {
    private Controller controller = new MockController();

    @Test
    public void testSaveLoadLocally() {
        var oldSchedule = controller.getSchedule();
        var oldUser = controller.getUser();
        controller.saveLocally();
        controller.loadLocally();
        var newSchedule = controller.getSchedule();
        var newUser = controller.getUser();

        assertEquals(oldSchedule, newSchedule);
        assertEquals(oldUser, newUser);
    }
}
