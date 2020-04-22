package edu.baylor.csi3471.netime_planner.models.persistence.impl;

import edu.baylor.csi3471.netime_planner.models.domain_objects.Group;
import edu.baylor.csi3471.netime_planner.models.persistence.DatabaseDAO;
import edu.baylor.csi3471.netime_planner.models.persistence.GroupDAO;

import java.util.Optional;

public class GroupDbDAO extends DatabaseDAO<Group> implements GroupDAO {
    @Override
    public Optional<Group> doFindById(int id) {
        return Optional.empty();
    }

    @Override
    public void doDelete(Group obj) {

    }

    @Override
    protected void doUpdate(Group obj) {

    }

    @Override
    protected void doInsert(Group obj) {

    }
}
