package homework.context;

import homework.annotation.Autowired;
import homework.annotation.Bean;
import homework.annotation.Configuration;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ApplicationContext {

    private final Reflections reflections;

    private final Map<Class<?>, Object> allBeans = new ConcurrentHashMap<>();

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
                        bean = x.invoke(x.getDeclaringClass().newInstance());
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                    if (Objects.nonNull(bean)) {
                        allBeans.put(bean.getClass(), bean);
                    }
                });
    }

    private List<?> getAllConfigurationsClass() {
        return reflections.getTypesAnnotatedWith(Configuration.class).stream().map(type -> {
            try {
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
                        x.invoke(beanEntry.getKey().newInstance(), getBean(x.getParameterTypes()[0]));
                    } catch (IllegalAccessException | InvocationTargetException |InstantiationException e) {
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
}
