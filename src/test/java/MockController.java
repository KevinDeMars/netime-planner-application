import edu.baylor.csi3471.netime_planner.models.Controller;

import java.io.File;

public class MockController extends Controller {
    public MockController() {
        loadLocally(new File("mock-controller-data.xml"));
    }

    @Override
    public boolean saveLocally() {
        return saveLocally(new File("mock-controller-testcopy.xml"));
    }

    @Override
    public boolean loadLocally() {
        return loadLocally(new File("mock-controller-testcopy.xml"));
    }
}
