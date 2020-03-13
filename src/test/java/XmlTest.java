import edu.baylor.csi3471.netime_planner.models.Activity;
import edu.baylor.csi3471.netime_planner.models.Deadline;
import edu.baylor.csi3471.netime_planner.models.Event;
import edu.baylor.csi3471.netime_planner.models.Schedule;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class XmlTest {

    @Test
    public void testSaveSchedule() throws JAXBException {
        var schedule = ScheduleTest.makeTestSchedule();
        // I think you have to include types that you use polymorphism with, and everything else automatically works?
        var ctx = JAXBContext.newInstance(Schedule.class, Event.class, Deadline.class, Activity.class);
        var marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(schedule, new File("test.xml"));
    }
}
