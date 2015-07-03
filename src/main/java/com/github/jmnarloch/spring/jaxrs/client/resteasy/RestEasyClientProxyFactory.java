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
package com.github.jmnarloch.spring.jaxrs.client.resteasy;

import com.github.jmnarloch.spring.jaxrs.client.support.ClientBuilderConfigurer;
import com.github.jmnarloch.spring.jaxrs.client.support.ClientBuilderHolder;
import com.github.jmnarloch.spring.jaxrs.client.support.JaxRsClientProxyFactory;
import com.github.jmnarloch.spring.jaxrs.client.support.JaxRsClientProxyFactorySupport;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * The RESTEasy {@link JaxRsClientProxyFactory} implementation.
 * It delegates the execution to {@link ResteasyClientBuilder} create the proxy instances.
 *
 * @author Jakub Narloch
 */
class RestEasyClientProxyFactory extends JaxRsClientProxyFactorySupport {

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T createClientProxy(Class<T> serviceClass, String serviceUrl) {

        // creates the client builder instance
        final ClientBuilder builder = clientBuilder();

        // configures the builder
        configure(builder);

        // creates the proxy instance
        return proxy(serviceClass, serviceUrl, builder.build());
    }

    /**
     * Creates the new instance of {@link ClientBuilder}.
     *
     * @return the client builder
     */
    private ClientBuilder clientBuilder() {

        return ClientBuilder.newBuilder();
    }

    /**
     * Configures the client builder.
     *
     * @param builder the client builder
     */
    private void configure(ClientBuilder builder) {

        final ClientBuilderHolder<?> holder = new ClientBuilderHolder<>(builder);
        final ClientBuilderConfigurer configurer = new ClientBuilderConfigurer(holder);

        // configures the builder
        configureClientBuilder(configurer);

        // registers the providers
        registerProviders(holder.getClientBuilder(), getProviders());
    }

    /**
     * Creates the proxy instance.
     *
     * @param serviceClass the service class
     * @param serviceUrl   the service url
     * @param client       the client
     * @param <T>          the proxy type
     * @return the proxy instance
     */
    private <T> T proxy(Class<T> serviceClass, String serviceUrl, Client client) {

        final WebTarget target = client.target(serviceUrl);
        return ((ResteasyWebTarget) target).proxy(serviceClass);
    }

    /**
     * Registers the provider classes.
     *
     * @param clientBuilder the client builder
     * @param providers     the providers classes
     */
    private void registerProviders(ClientBuilder clientBuilder, Class<?>[] providers) {

        for (Class<?> filter : providers) {
            clientBuilder.register(filter);
        }
    }
}
