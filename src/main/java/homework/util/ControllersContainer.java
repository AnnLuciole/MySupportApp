package homework.util;

import homework.annotation.Autowired;
import homework.annotation.Controller;
import homework.annotation.Mapping;
import homework.context.ApplicationContext;
import homework.enums.HttpMethod;
import homework.rest.SupportController;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ControllersContainer {

    private ApplicationContext context;
    private static final Map<String, SupportController> servlets;
    private static final Map<String, Map<HttpMethod, Method>> mappings;

    static {
        servlets = new HashMap<>();
        mappings = new HashMap<>();
    }

    public void initContainer() {
        context.getAllBeans().values().stream()
                .filter(x -> x.getClass().isAnnotationPresent(Controller.class))
                .forEach(y -> {
                    String classPath = y.getClass().getAnnotation(Mapping.class).path();
                    addNewController(classPath, (SupportController) y);
                    Arrays.stream(y.getClass().getDeclaredMethods())
                            .filter(method -> method.isAnnotationPresent(Mapping.class))
                            .forEach(z -> {
                                HttpMethod type = z.getAnnotation(Mapping.class).type();
                                String path = z.getAnnotation(Mapping.class).path();
                                if (Objects.nonNull(path)) {
                                    path = z.getDeclaringClass().getAnnotation(Mapping.class).path() + path;
                                    addNewController(path, (SupportController) y);
                                }
                                addNewMethod(path, type, z);
                            });
                }
                );
    }

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    private void addNewController(String path, SupportController controller) {
        servlets.put(path, controller);
    }

    public SupportController getController(String path) {
        return servlets.get(path);
    }

    private Map<HttpMethod, Method> getMethodsMap(String path) {
        if (Objects.isNull(mappings.get(path))) {
            return new ConcurrentHashMap<>();
        }
        return mappings.get(path);
    }

    private void addNewMethod(String path, HttpMethod type, Method method) {
        Map<HttpMethod, Method> methodsMap = getMethodsMap(path);
        methodsMap.put(type, method);
        mappings.put(path, methodsMap);
    }

    public Map<HttpMethod, Method> getMethod(String path) {
        return mappings.get(path);
    }
}
