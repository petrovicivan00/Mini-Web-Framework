package framework.engine;

import framework.annotations.*;
import framework.engine.routes.ControllerRoute;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClassHelper {

    public static Map<String, ControllerRoute> initializeControllers(Set<Class<?>> controllers) {

        Map<String, ControllerRoute> routes = new HashMap<>();

        for (Class<?> c : controllers) {
            Object controller = instantiateClass(c);
            Method[] methods = c.getDeclaredMethods();

            for (Method m : methods) {

                String route = "";

                if(m.isAnnotationPresent(Path.class)) {

                    if (m.isAnnotationPresent(GET.class)) {
                        route = "GET:" + m.getAnnotation(Path.class).path();
                    } else if (m.isAnnotationPresent(POST.class)) {
                        route = "POST:" + m.getAnnotation(Path.class).path();
                    }
                    ControllerRoute cr = new ControllerRoute(controller, m);
                    routes.put(route, cr);
                }
            }
        }
        return routes;
    }

    public static Object instantiateClass(Class<?> c) {

        try {
            Object object = c.getDeclaredConstructor().newInstance();
            Field[] fields = c.getDeclaredFields();

            for (Field f : fields) {

                f.setAccessible(true);
                Class<?> type = f.getType();

                if (f.isAnnotationPresent(Autowired.class)) {

                    if (type.isInterface() && !f.isAnnotationPresent(Qualifier.class))
                        throw new RuntimeException("Interface: " + f.getName() + ", in class: " + c.getName() + " needs @Qualifier annotation!");

                    Object objectField;

                    if (type.isInterface() && f.isAnnotationPresent(Qualifier.class)) {

                        String value = f.getAnnotation(Qualifier.class).value();
                        Class<?> classImplementation = DependencyContainer.getInstance().find(value);
                        objectField = instantiateClass(classImplementation);

                    }else if (type.isAnnotationPresent(Bean.class) | type.isAnnotationPresent(Service.class)) {

                        ScopeType scopeType = ScopeType.PROTOTYPE;

                        if(type.isAnnotationPresent(Bean.class))
                            scopeType = type.getAnnotation(Bean.class).scope();

                        if(type.isAnnotationPresent(Service.class))
                            scopeType = ScopeType.SINGLETON;

                        if (scopeType == ScopeType.SINGLETON) {
                            objectField = DIEngine.getInstance().getServiceOrBeanByClass(type);

                            if (objectField == null) {
                                objectField = instantiateClass(type);
                                DIEngine.getInstance().addServiceOrBean(type, objectField);
                            }
                        } else {
                            objectField = instantiateClass(type);
                        }

                    } else if (type.isAnnotationPresent(Component.class)) {
                        objectField = instantiateClass(type);
                    } else {
                        throw new RuntimeException("Class: " + type.getName() + " has no annotations!");
                    }
                    f.set(object, objectField);

                    boolean verbose = f.getAnnotation(Autowired.class).verbose();
                    if (verbose) {
                        System.out.println("Initialized <" + type.getName() + "> <" + f.getName() + "> in <" + c.getName() + "> on <" + LocalDateTime.now() + ">" + "with <" + objectField.hashCode() + ">"
                        );
                    }
                }
            }
            return object;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
