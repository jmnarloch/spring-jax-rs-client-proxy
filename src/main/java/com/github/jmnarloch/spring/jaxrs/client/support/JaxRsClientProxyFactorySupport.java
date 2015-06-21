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
package com.github.jmnarloch.spring.jaxrs.client.support;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * A base class for for the specific {@link JaxRsClientProxyFactory}.
 *
 * @author Jakub Narloch
 */
public abstract class JaxRsClientProxyFactorySupport implements JaxRsClientProxyFactory {

    /**
     * The composite configurers.
     */
    private final JaxRsClientConfigurerComposite configurers = new JaxRsClientConfigurerComposite();

    /**
     * The additional providers.
     */
    private Class<?>[] providers;

    /**
     * Sets the configurers.
     *
     * @param configurers the configurers
     */
    @Autowired(required = false)
    public void setConfigurers(List<JaxRsClientConfigurer> configurers) {

        this.configurers.addAll(configurers);
    }

    /**
     * Retrieves the providers.
     *
     * @return the providers
     */
    protected Class<?>[] getProviders() {

        if (providers == null) {
            final ProviderRegistry providerRegistry = new ProviderRegistry();
            registerProviders(providerRegistry);
            final List<Class<?>> providerList = providerRegistry.getProviders();
            this.providers = providerList.toArray(new Class[providerList.size()]);
        }
        return providers;
    }

    /**
     * Registers the providers.
     *
     * @param providerRegistry the provider registry
     */
    protected void registerProviders(ProviderRegistry providerRegistry) {

        configurers.registerProviders(providerRegistry);
    }
}
