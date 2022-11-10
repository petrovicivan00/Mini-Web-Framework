package framework.engine.routes;

import framework.http.request.Request;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ControllerRoute{

    private final Object controller;
    private final Method method;

    public ControllerRoute(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    public Object invokeMethod(Request request) {

        Class<?>[] params = method.getParameterTypes();

        if (params.length == 0) {

            try {
                return method.invoke(controller);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else if (params.length == 1 && params[0].equals(Request.class)) {

            try {
                return method.invoke(controller, request);
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
