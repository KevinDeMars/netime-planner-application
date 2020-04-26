package edu.baylor.csi3471.netime_planner.models.persistence;

import edu.baylor.csi3471.netime_planner.models.domain_objects.DomainObject;
import edu.baylor.csi3471.netime_planner.services.ServiceManager;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class DatabaseDAO<T extends DomainObject> implements DAO<T> {
    private final Map<Integer, T> cache = new HashMap<>();
    protected final Connection conn = ServiceManager.getInstance().getService(Connection.class);

    public void clearCache() {
        cache.clear();
    }

    @Override
    public final void save(T obj) {
        if (obj.getId() != null && findById(obj.getId()).isPresent())
            doUpdate(obj);
        else
            doInsert(obj);
        cache.put(obj.getId(), obj);
    }

    @Override
    public final Optional<T> findById(int id) {
        if (!cache.containsKey(id)) {
            var result = doFindById(id);
            cache.put(id, result.orElse(null));
            return result;
        }
        return Optional.ofNullable(cache.get(id));
    }

    public void delete(T obj) {
        doDelete(obj);
        cache.put(obj.getId(), null);
    }

    protected abstract Optional<T> doFindById(int id);
    protected abstract void doDelete(T obj);
    protected abstract void doUpdate(T obj);
    protected abstract void doInsert(T obj);

}
