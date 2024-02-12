package homework.util;

import homework.enums.MappingType;
import homework.rest.SupportController;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ControllersContainer {
    private static final Map<String, SupportController> servlets;
    private static final Map<String, Map<MappingType, Method>> mappings;

    static {
        servlets = new ConcurrentHashMap<>();
        mappings = new ConcurrentHashMap<>();
    }

    public void addNewController(String path, SupportController controller) {
        servlets.put(path, controller);
    }

    public SupportController getController(String path) {
        return servlets.get(path);
    }

    private Map<MappingType, Method> getMethodsMap(String path) {
        if (Objects.isNull(mappings.get(path))) {
            return new ConcurrentHashMap<>();
        }
        return mappings.get(path);
    }

    public void addNewMethod(String path, MappingType type, Method method) {
        Map<MappingType, Method> methodsMap = getMethodsMap(path);
        methodsMap.put(type, method);
        mappings.put(path, methodsMap);
    }

    public Map<MappingType, Method> getMethod(String path) {
        return mappings.get(path);
    }
}
