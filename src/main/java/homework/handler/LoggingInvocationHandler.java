package homework.handler;

import homework.annotation.Logging;

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
            System.out.println("Start");
            Object result = method.invoke(object, args);
            System.out.println("Finish");
            return result;
        } else {
            return method.invoke(object, args);
        }
    }
}
