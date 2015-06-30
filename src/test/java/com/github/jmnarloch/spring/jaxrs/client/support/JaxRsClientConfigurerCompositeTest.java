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

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.ext.Provider;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests the {@link JaxRsClientConfigurerComposite} class.
 *
 * @author Jakub Narloch
 */
public class JaxRsClientConfigurerCompositeTest {

    /**
     * Represents the instance of the tested class.
     */
    private JaxRsClientConfigurerComposite instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new JaxRsClientConfigurerComposite();
    }

    /**
     * Tests the registration of the providers.
     */
    @Test
    public void shouldNotRegisterProvider() {

        final ProviderRegistry registry = new ProviderRegistry();
        registry.addProvider(SimpleProvider.class);

        // when
        instance.registerProviders(registry);
    }

    /**
     * Tests the registration of the providers.
     */
    @Test
    public void shouldRegisterProvider() {

        // given
        final List<JaxRsClientConfigurer> configurers = Arrays.asList(
                mock(JaxRsClientConfigurer.class),
                mock(JaxRsClientConfigurer.class)
        );
        instance.addAll(configurers);
        final ProviderRegistry registry = new ProviderRegistry();

        // when
        instance.registerProviders(registry);

        // then
        for(JaxRsClientConfigurer conf : configurers) {
            verify(conf).registerProviders(registry);
        }
    }

    /**
     * A simple provider class used for testing.
     *
     * @author Jakub Narloch
     */
    @Provider
    private static class SimpleProvider {

    }
}