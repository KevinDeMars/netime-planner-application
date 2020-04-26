package edu.baylor.csi3471.netime_planner.services;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException() {
        super();
    }
    public ServiceNotFoundException(String message) {
        super(message);
    }
}

public class ServiceManager {
    private static final Logger LOGGER = Logger.getLogger(ServiceManager.class.getName());
    private static ServiceManager instance;
    public static ServiceManager getInstance() {
        if (instance == null) {
            synchronized (ServiceManager.class) {
                if (instance == null) {
                    instance = new ServiceManager();
                }
            }
        }
        return instance;
    }

    private final Map<Class<?>, Object> services = new HashMap<>();

    public <T> void addService(Class<T> serviceType, T serviceImpl) {
        if (services.containsKey(serviceType))
            LOGGER.warning("Service already exists: " + serviceType.getName());
        services.put(serviceType, serviceImpl);
    }

    public <T> T getService(Class<T> serviceType) {
        if (!services.containsKey(serviceType))
            throw new ServiceNotFoundException("Service not found: " + serviceType.getName());
        return (T) services.get(serviceType);
    }
}
