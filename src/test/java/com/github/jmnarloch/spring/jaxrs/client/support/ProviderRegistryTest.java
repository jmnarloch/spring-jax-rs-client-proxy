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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests the {@link ProviderRegistry} class.
 *
 * @author Jakub Narloch
 */
public class ProviderRegistryTest {

    /**
     * The instance of the tested class.
     */
    private ProviderRegistry instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new ProviderRegistry();
    }

    /**
     * Tests registration of the provider classes.
     */
    @Test
    public void shouldRegisterClasses() {

        // when
        instance.addProvider(SimpleProvider.class);

        // then
        assertNotNull(instance.getProviders());
        assertEquals(1, instance.getProviders().size());
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