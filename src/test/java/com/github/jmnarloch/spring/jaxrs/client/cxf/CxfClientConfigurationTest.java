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
package com.github.jmnarloch.spring.jaxrs.client.cxf;

import com.github.jmnarloch.spring.jaxrs.client.support.JaxRsClientProxyFactory;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link CxfClientConfiguration} class.
 *
 * @author Jakub Narloch
 */
public class CxfClientConfigurationTest {

    /**
     * Tests the registration of the proxy factory in application context.
     */
    @Test
    public void shouldRegisterClientProxyFactory() {

        // given
        final AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(CxfClientConfiguration.class);

        // when
        JaxRsClientProxyFactory factory = context.getBean(JaxRsClientProxyFactory.class);

        // then
        assertNotNull(factory);
        assertTrue(CxfClientProxyFactory.class.equals(factory.getClass()));
    }
}