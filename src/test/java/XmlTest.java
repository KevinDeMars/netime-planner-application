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
        var ctx = JAXBContext.newInstance(Schedule.class);
        var marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(schedule, new File("test.xml"));
    }
}
