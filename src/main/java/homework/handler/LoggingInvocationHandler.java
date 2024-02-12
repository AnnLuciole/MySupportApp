package homework.handler;

import homework.Phrase;
import homework.annotation.Logging;
import homework.annotation.Mapping;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggingInvocationHandler implements InvocationHandler {

    private final Object object;

    public LoggingInvocationHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.isAnnotationPresent(Logging.class)) {
            String path = method.getDeclaringClass().getAnnotation(Mapping.class).path() +
                    method.getAnnotation(Mapping.class).path();
            String params = args.toString();
            System.out.println(String
                    .format("Called method %s with path %s and params %s", method.getName(), path, params));
            Object result = method.invoke(object, args);
            System.out.println(String
                    .format("Finished method %s with path %s and params %s", method.getName(), path, params));
            return result;
        } else {
            return method.invoke(object, args);
        }
    }
}
