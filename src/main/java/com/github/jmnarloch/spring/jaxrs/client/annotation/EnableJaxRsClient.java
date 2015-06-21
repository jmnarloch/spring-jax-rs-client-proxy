/**
 * Copyright (c) 2015 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jmnarloch.spring.jaxrs.client.annotation;

import com.github.jmnarloch.spring.jaxrs.client.support.JaxRsClientRegistrar;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enables the proxy client generation of specific class path scanned interfaces.
 *
 * Each interface annotated with {@link javax.ws.rs.Path} will be wrapped into proxy client class and registered in the
 * {@link ApplicationContext}.
 *
 * @author Jakub Narloch
 * @see com.github.jmnarloch.spring.jaxrs.client.resteasy.EnableRestEasyClient
 * @see com.github.jmnarloch.spring.jaxrs.client.cxf.EnableCxfClient
 * @see com.github.jmnarloch.spring.jaxrs.client.jersey.EnableJerseyClient
 */
@Import(JaxRsClientRegistrar.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableJaxRsClient {

    /**
     * The alias for {@link #basePackages()}. Specifies the package names for class path scanning of JAX-RS resources.
     *
     * Specifying both attributes is additive.
     */
    String[] value() default {};

    /**
     * Specifies the package names for class path scanning of JAX-RS resources.
     *
     * Specifying both attributes is additive.
     */
    String[] basePackages() default {};

    /**
     * Specifies the list of optional providers to register.
     */
    Class<?>[] providers() default {};

    /**
     * The service url. The url of each independent service will be resolved as relative to this url.
     *
     * Either {@link #serviceUrl()} or {@link #serviceUrlProvider()} must be specified.
     */
    String serviceUrl() default "";

    /**
     * The service url provider that can be used to retrieve the remote service url.
     * This property defines the concreate type of bean that will lookup from
     * {@link org.springframework.context.ApplicationContext} in order to acquire the url.
     *
     * Either {@link #serviceUrl()} or {@link #serviceUrlProvider()} must be specified.
     */
    Class<? extends ServiceUrlProvider> serviceUrlProvider() default ServiceUrlProvider.class;
}
