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
package com.github.jmnarloch.spring.jaxrs.client;

import com.github.jmnarloch.spring.jaxrs.client.annotation.EnableJaxRsClient;
import com.github.jmnarloch.spring.jaxrs.client.annotation.ServiceUrlProvider;
import com.github.jmnarloch.spring.jaxrs.client.resteasy.EnableRestEasyClient;
import com.github.jmnarloch.spring.jaxrs.client.support.JaxRsClientConfigurer;
import com.github.jmnarloch.spring.jaxrs.client.support.ProviderRegistry;
import com.github.jmnarloch.spring.jaxrs.resource.EchoResource;
import org.jboss.resteasy.plugins.interceptors.encoding.GZIPDecodingInterceptor;
import org.jboss.resteasy.plugins.interceptors.encoding.GZIPEncodingInterceptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Demonstrates the usage of this component.
 *
 * @author Jakub Narloch
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class Demo {

    @Autowired
    private EchoResource echoResource;

    @Test
    public void demo() {

    }

    @EnableJaxRsClient(
            basePackages = "com.github.jmnarloch.spring.jaxrs.resource",
            serviceUrl = "localhost:8080/api"
    )
    @EnableRestEasyClient
    @Configuration
    public static class TestConfig implements JaxRsClientConfigurer {

        @Override
        public void registerProviders(ProviderRegistry providerRegistry) {

            providerRegistry
                    .addProvider(GZIPEncodingInterceptor.class)
                    .addProvider(GZIPDecodingInterceptor.class);
        }
    }
}