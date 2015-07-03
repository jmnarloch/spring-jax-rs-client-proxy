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

import com.github.jmnarloch.spring.jaxrs.resource.EchoResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests the {@link JaxRsClientProxyFactoryBean} class.
 *
 * @author Jakub Narloch
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JaxRsClientProxyFactoryBeanTest {

    private JaxRsClientProxyFactoryBean instance;

    @Autowired
    private ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {

        instance = new JaxRsClientProxyFactoryBean();
    }

    @Test
    public void shouldResolveSpelProperty() throws Exception {

        final Class<EchoResource> serviceClass = EchoResource.class;
        final JaxRsClientProxyFactory factory = applicationContext.getBean(JaxRsClientProxyFactory.class);

        instance.setApplicationContext(applicationContext);
        instance.setServiceClass(serviceClass);
        instance.setServiceUrl("#{environment['rest.api.service.url']}");

        final Object proxy = instance.getObject();
        verify(factory).createClientProxy(serviceClass, "http://app.com/rest/api");
    }

    /**
     * Test config.
     */
    @PropertySources(
        @PropertySource("classpath:config/app.properties")
    )
    @Configuration
    public static class TestConfig {

        @Bean
        public JaxRsClientProxyFactory factory() {
            return mock(JaxRsClientProxyFactory.class);
        }
    }
}