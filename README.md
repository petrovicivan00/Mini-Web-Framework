# Mini Web Framework

This is a simple web framework developed as a Java project, inspired by JAX-RS and Spring. The framework allows you to create web applications based on controllers with methods that handle incoming requests and generate JSON responses. It also supports dependency injection at the controller level.

## Features

### Route Registration

You can define available routes in your application by annotating controller methods with the `@Path` annotation, which requires a mandatory parameter, the path. You can also specify the HTTP method for that route using the `@GET` or `@POST` annotations. For example, if you have a controller method annotated with `@Path("/test")` and `@GET`, it means that when a client sends a GET HTTP request to the path `/test`, it will trigger the corresponding controller method responsible for returning a response to the client.

### Dependency Injection

The framework achieves inversion of control (IoC) starting from the controller's attributes. Dependency injection is handled by a separate class, the DI Engine. You can annotate controller attributes that need to be injected with the `@Autowired` annotation. This annotation has an optional `verbose` parameter, which, when set to true, displays additional information about the injected object during initialization. Dependency injection can handle two cases:

1. Annotated attribute with a specific class type.
2. Annotated attribute with an interface type, requiring a Dependency Container to map the interface to its concrete implementation.

The following annotations are provided for dependency injection:

- `@Autowired`: Marks attributes for injection.
- `@Bean`: Marks classes. You can specify the scope (singleton or prototype) with the `scope` parameter.
- `@Service` and `@Component`: Behave like `@Bean` with predefined scope values.
- `@Qualifier`: Resolves which specific bean to inject. It can annotate classes or attributes.

### Dependency Container

The Dependency Container allows you to register concrete implementations for interfaces used in dependency injection. It throws an exception if a type is not registered in the container but is requested.

### DI Engine

The DI Engine is responsible for initializing all annotated dependencies using reflection, starting from the controller. It can display information if the `verbose` parameter in the `@Autowired` annotation is set to true. The DI Engine manages instances annotated with `@Bean(scope="singleton")` or `@Service` and initializes them only once during the application's execution. It also handles the initialization of class attributes using recursion. When it encounters an interface, it consults the Dependency Container and uses the Qualifier to get the appropriate implementation.

## Technical Requirements

- Each class should have a default (no-argument) constructor.
- Attributes should not be static or final.
- No class should inherit from another class.
- There should be no circular dependencies among dependencies.

## Usage

To use this framework, you can follow these steps:

1. Clone the repository.

2. Create your controllers by annotating methods with `@Path`, `@GET`, or `@POST` as needed.

3. Annotate attributes that need dependency injection with `@Autowired`.

4. Optionally use `@Bean`, `@Service`, and `@Component` annotations to mark classes and specify their scopes.

5. When running your application, the framework will scan the classes, discover the controllers, and initialize the dependencies.

6. Handle incoming requests, and the framework will route them to the appropriate controller methods based on the annotated paths and HTTP methods.

7. Enjoy the benefits of a lightweight web framework with dependency injection!

Please note that this framework does not rely on external libraries for dependency injection.
