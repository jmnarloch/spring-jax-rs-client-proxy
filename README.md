# Spring JAX-RS Client Proxy

A tool for easier registration of proxy based JAX-RS clients in Spring.

[![Build Status](https://travis-ci.org/jmnarloch/spring-jax-rs-client-proxy.svg?branch=master)](https://travis-ci.org/jmnarloch/spring-jax-rs-client-proxy)

## Features

Simple setup of the JAX-RS client proxies based on the classpath scanning.

## Support

- RESTEasy
- CXF
- Jersey

## Setup

In order to start and running simply add the artifact to your Maven/Gradle build.

```
<dependency>
    <groupId>com.github.jmnarloch</groupId>
    <artifactId>spring-jax-rs-client-proxy</artifactId>
    <version>1.0.1</version>
</dependency>
```

## Example

Start by defining the resource interface annotated with JAX-RS annotations.

```
@Path("/echo")
public interface EchoResource {

    @GET
    String get(@PathParam("name") String name);
}
```

Enable the proxy creation on your configuration class and add one of meta annotations: `@EnableRestEasyClient`, `@EnableCxfClient` or `@EnableJerseyClient`. 

```
@EnableJaxRsClient(
    basePackages = "com.app.rest.api",
    serviceUrl = "localhost:8080/api"
)
@EnableRestEasyClient
@Configuration
public class AppConfig {

}
```

Inject the proxy into any Spring bean.


```
public class Service {

    @Autowired
    private EchoResource echoResource;

    ...
}
```

See the [documentation](doc.adoc) for additional examples
