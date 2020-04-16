package edu.baylor.csi3471.netime_planner.models.persistence;

import edu.baylor.csi3471.netime_planner.models.domain_objects.DomainObject;

import java.util.Optional;

public abstract class DatabaseDAO<T extends DomainObject> implements DAO<T> {
    @Override
    public final void save(T obj) {
        if (obj.getId() != null && findById(obj.getId()).isPresent())
            doUpdate(obj);
        else
            doInsert(obj);
    }

    @Override
    public abstract Optional<T> findById(Integer id);

    @Override
    public abstract void delete(T obj);

    protected abstract void doUpdate(T obj);
    protected abstract void doInsert(T obj);

}
