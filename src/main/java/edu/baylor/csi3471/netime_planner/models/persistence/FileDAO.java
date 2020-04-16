package edu.baylor.csi3471.netime_planner.models.persistence;

import edu.baylor.csi3471.netime_planner.models.adapters.XmlMapAdapter;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Activity;
import edu.baylor.csi3471.netime_planner.models.domain_objects.Deadline;
import edu.baylor.csi3471.netime_planner.models.domain_objects.DomainObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileDAO<T extends DomainObject> implements DAO<T> {
    private static final Logger LOGGER = Logger.getLogger(FileDAO.class.getName());
    private static final Map<Class<?>, HashMap<Integer, ?>> cache = new HashMap<>();
    protected final Class<?> type;

    public FileDAO(Class<T> type) {
        this.type = type;
        LOGGER.info("Type is " + type);
        loadDataFromFile();
    }

    private void loadDataFromFile() {
        if (!cache.containsKey(type)) {
            var f = new File(type.getName());
            if (!f.exists()) {
                LOGGER.info("Data not found for " + type.getName());
                cache.put(type, new HashMap<>());
            }
            else {
                try {
                    var ctx = JAXBContext.newInstance(Deadline.class, Activity.class);
                    var unmarshaller = ctx.createUnmarshaller();
                    var data = (HashMap<Integer, ?>) unmarshaller.unmarshal(f);
                    cache.put(type, data);
                    LOGGER.info("Loaded data from " + f.getName());
                } catch (JAXBException e) {
                    LOGGER.log(Level.WARNING, "Couldn't load " + f.getName(), e);
                }
            }
        }
    }

    protected HashMap<Integer, T> getDataFromCache() {
        return (HashMap<Integer, T>) cache.get(type);
    }

    protected int getMaxId() {
        return getDataFromCache().keySet().stream().mapToInt(key -> key).max().orElse(1);
    }

    @Override
    public void save(T obj) {
        var data = getDataFromCache();
        if (obj.getId() == null)
            obj.setId(getMaxId() + 1);
        data.put(obj.getId(), obj);

        cache.put(type, data);
        flushCache();
    }

    protected void flushCache() {
        var f = new File(type.getName());
        try {
            var ctx = JAXBContext.newInstance(HashMap.class, Deadline.class, Activity.class);
            var marshaller = ctx.createMarshaller();
            marshaller.setAdapter(new XmlMapAdapter<Integer, T>());
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(getDataFromCache(), f);
            LOGGER.info("Saved to " + type.getName());
        } catch (JAXBException e) {
            LOGGER.log(Level.WARNING, "Couldn't save " + f.getName(), e);
        }
    }

    @Override
    public Optional<T> findById(int id) {
        return Optional.ofNullable(getDataFromCache().get(id));
    }

    @Override
    public void delete(T obj) {
        getDataFromCache().remove(obj.getId());
        flushCache();
    }


}
