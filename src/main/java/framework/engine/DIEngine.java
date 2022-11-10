package framework.engine;

import framework.annotations.Controller;
import framework.annotations.Qualifier;
import framework.engine.routes.ControllerRoute;

import java.io.File;
import java.util.*;

public class DIEngine {

    private static DIEngine instance;

    private final Set<Class<?>> controllers;
    private final Set<Class<?>> qualifiers;
    private final Map<Class<?>, Object> servicesAndBeans;
    private Map<String, ControllerRoute> routes;

    private DIEngine() {
        this.controllers = new HashSet<>();
        this.qualifiers = new HashSet<>();
        this.servicesAndBeans = new HashMap<>();
    }

    public static DIEngine getInstance() {
        if (instance == null)
            instance = new DIEngine();

        return instance;
    }

    public void setup() {

        File dir = new File(System.getProperty("user.dir"));
        loadAllClasses(Objects.requireNonNull(dir.listFiles()));
        DependencyContainer.getInstance().initQualifiers(qualifiers);
        routes = ClassHelper.initializeControllers(controllers);
    }

    public Class<?> getClass(String className) {

        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadAllClasses(File[] files) {

        for (File f : files) {
            if (f.isDirectory()) {
                loadAllClasses(Objects.requireNonNull(f.listFiles()));
            } else if (f.getName().endsWith(".class")) {
                String userDir = System.getProperty("user.dir");

                String name = f.getAbsolutePath().replace(userDir + "/target/classes/", "")
                                                      .replaceAll("/", ".")
                                                      .replace(".class", "");

                System.out.println(name);
                Class<?> c = getClass(name);
                if (c.isAnnotationPresent(Controller.class))
                    controllers.add(c);
                else if (c.isAnnotationPresent(Qualifier.class))
                    qualifiers.add(c);
            }
        }
    }

    public Object getServiceOrBeanByClass(Class<?> key) {
        return servicesAndBeans.get(key);
    }

    public void addServiceOrBean(Class<?> key, Object value) {
        servicesAndBeans.put(key, value);
    }

    public ControllerRoute getRoute(String path) {
        return routes.get(path);
    }
}
