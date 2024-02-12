package homework.context;

import homework.annotation.*;
import homework.enums.MappingType;
import homework.handler.LoggingInvocationHandler;
import homework.rest.SupportController;
import homework.util.ControllersContainer;
import org.reflections.Reflections;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ApplicationContext {

    private final Reflections reflections;

    private final Map<Class<?>, Object> allBeans = new ConcurrentHashMap<>();

    private final ControllersContainer container = new ControllersContainer();

    public ApplicationContext(){
        reflections = new Reflections("java.homework");
        putAllBeans();
        injectAllBeanFields();
    }

    private void putAllBeans() {
        List <?> allConfigs = getAllConfigurationsClass();
        allConfigs.stream()
                .map(x -> x.getClass().getDeclaredMethods())
                .flatMap(x -> Arrays.stream(x).distinct())
                .filter(x -> Objects.nonNull(x.getAnnotation(Bean.class)))
                .forEach(x -> {
                    Object bean;
                    try {
                        bean = x.invoke(getBean(x.getDeclaringClass()));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                    if (Objects.nonNull(bean)) {
                        if (Arrays.stream(bean.getClass().getDeclaredMethods())
                                .anyMatch(y -> y.isAnnotationPresent(Logging.class))) {
                            allBeans.put(bean.getClass(), wrapWithLoggingProxy(bean));
                        } else {
                            allBeans.put(bean.getClass(), bean);
                        }
                        if (bean.getClass().isAnnotationPresent(Controller.class)) {
                            putController(bean);
                        }
                    }
                });
    }

    private List<?> getAllConfigurationsClass() {
        return reflections.getTypesAnnotatedWith(Configuration.class).stream().map(type -> {
            try {
                allBeans.put(type, type.newInstance());
                return type.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    private void injectAllBeanFields() {
        allBeans.entrySet().forEach(this::injectBeanFields);
    }

    private void injectBeanFields(Map.Entry<Class<?>, Object> beanEntry) {
        Arrays.stream(beanEntry.getKey().getDeclaredMethods()).distinct()
                .filter(x -> Objects.nonNull(x.getAnnotation(Autowired.class)))
                .forEach(x -> {
                    try {
                        x.invoke(beanEntry.getValue(), getBean(x.getParameterTypes()[0]));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private Object getBean(Class<?> beanClass) {
        if (Objects.isNull(allBeans.get(beanClass))) {
            try {
                allBeans.put(beanClass, beanClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return allBeans.get(beanClass);
    }

    private void putController(Object object) {
        String classPath = object.getClass().getAnnotation(Mapping.class).path();
        container.addNewController(classPath, (SupportController) object);
        Arrays.stream(object.getClass().getDeclaredMethods())
                .filter(x -> x.isAnnotationPresent(Mapping.class))
                .forEach(z -> {
                    MappingType type = z.getAnnotation(Mapping.class).type();
                    String path = z.getAnnotation(Mapping.class).path();
                    if (Objects.nonNull(path)) {
                        path = z.getDeclaringClass().getAnnotation(Mapping.class).path() + path;
                        container.addNewController(path, (SupportController) object);
                    }
                    container.addNewMethod(path, type, z);
                });
    }

    private Object wrapWithLoggingProxy(Object object) {
        InvocationHandler handler = new LoggingInvocationHandler(object);
        allBeans.put(InvocationHandler.class, handler);
        return Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                (InvocationHandler) allBeans.get(InvocationHandler.class));
    }
}
