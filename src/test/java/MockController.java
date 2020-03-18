import edu.baylor.csi3471.netime_planner.models.Controller;

import java.io.File;

public class MockController extends Controller {
    public MockController() {
        loadLocally(new File("mock-controller-data.xml"));
    }
}
