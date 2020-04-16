package edu.baylor.csi3471.netime_planner.models.persistence;

import edu.baylor.csi3471.netime_planner.models.domain_objects.DomainObject;

import java.util.Optional;

public interface DAO<T extends DomainObject> {
    void save(T obj);
    Optional<T> findById(int id);
    void delete(T obj);
}
