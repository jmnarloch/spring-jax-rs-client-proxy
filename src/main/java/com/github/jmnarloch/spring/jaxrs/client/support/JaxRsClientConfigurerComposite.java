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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An composition of {@link JaxRsClientConfigurer} instances.
 *
 * @author Jakub Narloch
 */
class JaxRsClientConfigurerComposite implements JaxRsClientConfigurer {

    /**
     * The list of the configures.
     */
    private final List<JaxRsClientConfigurer> configurers = new ArrayList<JaxRsClientConfigurer>();

    /**
     * Adds all {@link JaxRsClientConfigurer} instances.
     *
     * @param configurers the configurers to add
     */
    void addAll(Collection<JaxRsClientConfigurer> configurers) {
        this.configurers.addAll(configurers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerProviders(ProviderRegistry providerRegistry) {

        for (JaxRsClientConfigurer configurer : configurers) {
            configurer.registerProviders(providerRegistry);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configureClientBuilder(ClientBuilderConfigurer configurer) {

        for (JaxRsClientConfigurer conf : configurers) {
            conf.configureClientBuilder(configurer);
        }
    }
}
