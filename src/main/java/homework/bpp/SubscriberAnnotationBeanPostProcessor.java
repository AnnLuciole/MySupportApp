package homework.bpp;

import homework.annotation.Subscriber;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class SubscriberAnnotationBeanPostProcessor implements BeanPostProcessor {

    Map<String, Method> allSubscribers = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        List<Method> methods = Arrays.stream(bean.getClass().getDeclaredMethods())
                .filter(x -> x.isAnnotationPresent(Subscriber.class))
                .collect(Collectors.toList());
        if (!methods.isEmpty()) {
            methods.forEach(x -> allSubscribers.put(beanName, x));
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method method = allSubscribers.get(beanName);
        if (Objects.nonNull(method)) {
            try {
                method.invoke(bean);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return bean;
    }
}
