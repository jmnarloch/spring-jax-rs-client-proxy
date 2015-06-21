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

import com.github.jmnarloch.spring.jaxrs.client.support.JaxRsClientProxyFactory;
import com.github.jmnarloch.spring.jaxrs.client.support.JaxRsClientProxyFactorySupport;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

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

        final ResteasyClientBuilder builder = new ResteasyClientBuilder();

        registerProviders(builder, getProviders());

        final ResteasyClient client = builder.build();
        final ResteasyWebTarget target = client.target(serviceUrl);
        return target.proxy(serviceClass);
    }

    /**
     * Registers the provider classes.
     *
     * @param builder  the builder
     * @param provider the provider classes
     */
    private void registerProviders(ResteasyClientBuilder builder, Class<?>[] provider) {

        for (Class<?> filter : provider) {
            builder.register(filter);
        }
    }
}
