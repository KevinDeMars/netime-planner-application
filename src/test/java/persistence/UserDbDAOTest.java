package persistence;

import edu.baylor.csi3471.netime_planner.models.domain_objects.User;
import edu.baylor.csi3471.netime_planner.models.persistence.DatabaseDAO;
import edu.baylor.csi3471.netime_planner.models.persistence.UserDbDAO;

public class UserDbDAOTest extends DbDAOTest<User> {

    @Override
    DatabaseDAO<User> getDAO() {
        return new UserDbDAO();
    }

    @Override
    User makeNewRow() {
        var u = new User();
        u.setName("test" + Math.random());
        u.setEmail("test@" + Math.random());
        u.setPasswordHash(new char[]{'a'});
        return u;
    }

    @Override
    void changeRow(User row) {
        row.setName("test" + Math.random());
    }
}
