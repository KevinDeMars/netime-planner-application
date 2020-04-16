package edu.baylor.csi3471.netime_planner.models.persistence;

import edu.baylor.csi3471.netime_planner.models.domain_objects.DomainObject;

import java.util.Optional;

public class FileDAO<T extends DomainObject> implements DAO<T> {
    @Override
    public void save(T obj) {
        throw new UnsupportedOperationException("Unimplemented"); // TODO
    }

    @Override
    public Optional<T> findById(Integer id) {
        throw new UnsupportedOperationException("Unimplemented"); // TODO
    }

    @Override
    public void delete(T obj) {
        throw new UnsupportedOperationException("Unimplemented"); // TODO
    }
}
