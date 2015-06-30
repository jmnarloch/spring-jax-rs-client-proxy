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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.ws.rs.ext.Provider;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

/**
 * Tests the {@link JaxRsClientProxyFactorySupport} class.
 *
 * @author Jakub Narloch
 */
public class JaxRsClientProxyFactorySupportTest {

    /**
     * The instance of the tested class.
     */
    private JaxRsClientProxyFactorySupport instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new MockJaxRsClientProxyFactorySupport();
    }

    @Test
    public void shouldRetrieveProviders() {

        // given
        final List<JaxRsClientConfigurer> configurers = Arrays.asList(
                mock(JaxRsClientConfigurer.class),
                mock(JaxRsClientConfigurer.class)
        );
        for(JaxRsClientConfigurer conf : configurers) {
            doAnswer(new Answer() {
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    ((ProviderRegistry)invocation.getArguments()[0]).addProvider(SimpleProvider.class);
                    return null;
                }
            }).when(conf).registerProviders(any(ProviderRegistry.class));
        }
        instance.setConfigurers(configurers);

        // when
        Class<?>[] providers = instance.getProviders();

        // then
        assertNotNull(providers);
        assertEquals(2, providers.length);
    }

    private static class MockJaxRsClientProxyFactorySupport extends JaxRsClientProxyFactorySupport {

        @Override
        public <T> T createClientProxy(Class<T> serviceClass, String serviceUrl) {
            return null;
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