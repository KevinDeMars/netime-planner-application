package edu.baylor.csi3471.persistence;

import edu.baylor.csi3471.netime_planner.models.domain_objects.Deadline;
import edu.baylor.csi3471.netime_planner.models.persistence.DatabaseDAO;
import edu.baylor.csi3471.netime_planner.models.persistence.impl.DeadlineDbDAO;

import java.time.LocalDate;

public class DeadlineDbDAOTest extends DbDAOTest<Deadline> {
    @Override
    DatabaseDAO<Deadline> getDAO() {
        return new DeadlineDbDAO();
    }

    @Override
    Deadline makeNewRow() {
        return new Deadline("aaa", "aaa", "location", LocalDate.now().atStartOfDay(), null, null);
    }

    @Override
    void changeRow(Deadline row) {
        row.setName("aaa" + Math.random());
    }
}
