package homework.context;

import homework.annotation.*;
import homework.handler.LoggingInvocationHandler;
import org.reflections.Reflections;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Context
public class ApplicationContext {

    private final Reflections reflections;

    private final Map<Class<?>, Object> allBeans = new ConcurrentHashMap<>();

    public ApplicationContext(){
        reflections = new Reflections("homework");
        init();
    }


    public Map<Class<?>, Object> getAllBeans() {
        return allBeans;
    }

    public Object getBeanForClass(Class<?> clazz) {
        return allBeans.get(clazz);
    }

    private void init() {
        putAllBeans();
        injectAllBeanFields();
    }

    private void putAllBeans() {
        List <?> allConfigs = getAllConfigurationsClass();
        allConfigs.stream()
                .map(x -> x.getClass().getDeclaredMethods())
                .flatMap(x -> Arrays.stream(x).distinct())
                .filter(x -> Objects.nonNull(x.getAnnotation(Bean.class)))
                .forEach(this::putNewBean);
    }

    private void putNewBean(Method method) {
        Object bean;
        Class clazz;
        Object configClass = getBean(method.getDeclaringClass());
        try {
            clazz  = method.getReturnType();
            bean = method.invoke(configClass);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        if (Objects.nonNull(bean)) {
            if (Arrays.stream(bean.getClass().getDeclaredMethods())
                    .anyMatch(y -> y.isAnnotationPresent(Logging.class))) {
                allBeans.put(clazz, wrapWithLoggingProxy(bean));
            } else {
                allBeans.put(clazz, bean);
            }
        }
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
        Iterator iter = allBeans.entrySet().iterator();
        while (iter.hasNext()){
            injectBeanFields((Map.Entry<Class<?>, Object>) iter.next());
        }
    }

    private void injectBeanFields(Map.Entry<Class<?>, Object> beanEntry) {
        Arrays.stream(beanEntry.getValue().getClass().getDeclaredMethods()).distinct()
                .filter(x -> Objects.nonNull(x.getAnnotation(Autowired.class)))
                .forEach(x -> {
                    try {
                        if (x.getParameterTypes().length == 0) {
                            x.invoke(beanEntry.getValue());
                        } else {
                            x.invoke(beanEntry.getValue(), getBean(x.getParameterTypes()[0]));
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private Object getBean(Class<?> beanClass) {
        if (Objects.isNull(allBeans.get(beanClass))) {
            if (beanClass.isAnnotationPresent(Context.class)) {
                return this;
            }
            try {
                allBeans.put(beanClass, beanClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return allBeans.get(beanClass);
    }

    private Object wrapWithLoggingProxy(Object object) {
        InvocationHandler handler = new LoggingInvocationHandler(object);
        allBeans.put(InvocationHandler.class, handler);
        return Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                (InvocationHandler) allBeans.get(InvocationHandler.class));
    }
}
