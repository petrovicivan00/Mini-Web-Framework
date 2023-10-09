# Mini Web Framework

This is a custom web framework inspired by JAX-rs/Spring, designed to help you create web applications with controllers, request handling, and JSON response generation. The framework supports dependency injection at the controller level and allows you to define routes using annotations.

## Features

### Route Registration
- Use the `@Controller` annotation to mark classes as controllers, indicating that they contain methods to handle client requests.
- Annotate controller methods with `@GET` or `@POST` to specify the HTTP methods that trigger the method's execution.
- Use the `@Path` annotation to define the route for the method to be activated.
- The framework discovers annotated classes and methods, mapping HTTP methods and routes to controllers.

### Dependency Injection
- Achieve Inversion of Control (IoC) by initializing controller attributes using dependency injection.
- Annotate controller attributes with `@Autowired` to indicate dependencies that should be injected during controller initialization.
- Support for injecting dependencies with attributes annotated with `@Autowired`, which can also have their dependencies.
- Two cases for initializing attributes annotated with `@Autowired`: concrete class type or interface type, with a `@Qualifier` option for interface-based injection.

### Annotations
- `@Autowired`: Mark attributes for dependency injection and optionally specify a `verbose` parameter for logging information.
- `@Bean`, `@Service`, and `@Component`: Mark classes for dependency injection and specify the scope (singleton or prototype).
- `@Qualifier`: Resolve which bean to inject by specifying a value for attribute or class-based injection.

### Dependency Container
- Register concrete implementations for interfaces in the Dependency Container.
- Throw an exception if a requested type is not found in the container.

### DI Engine
- Initialize dependencies recursively, starting from controllers.
- Log information if the `verbose` parameter of `@Autowired` is set to true.
- Manage instances of classes marked with `@Bean` or `@Service`, creating them only once during the application's execution.
- Consult the Dependency Container when encountering interfaces to resolve their implementations.

## Requirements

- Each class should have a default constructor.
- Attributes should not be static or final.
- No class should inherit from another class.
- Avoid circular dependencies.

## Technical Details

- Implement the solution using reflection or Aspect-Oriented Programming (AOP).
- Do not use external libraries for dependency injection.

## Getting Started

- Clone this repository.
- Configure your project.
- Implement your controllers and services.
- Run your application.

## Development Server

Run `ng serve` to start a development server. Navigate to `http://localhost:4200/` to access the application. The server automatically reloads when source files change.

## Building

Run `ng build` to build the project. Build artifacts are stored in the `dist/` directory.

## Running Tests

- Run `ng test` to execute unit tests via Karma.
- Run `ng e2e` to execute end-to-end tests.

## Further Help

For more information on the Angular CLI, use `ng help` or refer to the [Angular CLI documentation](https://angular.io/cli).
