package framework.engine;

import framework.annotations.Qualifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DependencyContainer {

    private static DependencyContainer instance;
    private final Map<String, Class<?>> qualifierClasses;

    private DependencyContainer() {
        this.qualifierClasses = new HashMap<>();
    }

    public static DependencyContainer getInstance() {
        if (instance == null)
            instance = new DependencyContainer();
        return instance;
    }

    public void initQualifiers(Set<Class<?>> qualifiers) {

        for (Class<?> q : qualifiers) {

            if (q.isAnnotationPresent(Qualifier.class)) {

                String value = q.getAnnotation(Qualifier.class).value();

                if (find(value) != null)
                    throw new RuntimeException("Class with @Qualifier annotaction already exists!");

                qualifierClasses.put(value, q);
            }
        }
    }

    public Class<?> find(String key) {
        return qualifierClasses.get(key);
    }
}
