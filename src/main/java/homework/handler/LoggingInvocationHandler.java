package homework.handler;

import homework.annotation.Logging;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class LoggingInvocationHandler implements InvocationHandler {

    private final Object object;

    private final Logger log = Logger.getLogger(LoggingInvocationHandler.class.getName());

    public LoggingInvocationHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.isAnnotationPresent(Logging.class)) {
            String path = method.getDeclaringClass().getAnnotation(RequestMapping.class).path()[0] +
                    method.getAnnotation(RequestMapping.class).path()[0];
            String params = args.toString();
            log.info(String.format("Called method %s with path %s and params %s", method.getName(), path, params));
            Object result = method.invoke(object, args);
            log.info(String.format("Finished method %s with path %s and params %s", method.getName(), path, params));
            return result;
        } else {
            return method.invoke(object, args);
        }
    }
}
