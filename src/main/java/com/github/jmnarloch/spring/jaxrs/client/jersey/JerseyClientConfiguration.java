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
package com.github.jmnarloch.spring.jaxrs.client.jersey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registers all the needed beans to enable Jersey client proxy generation.
 *
 * @author Jakub Narloch
 */
@Configuration
class JerseyClientConfiguration {

    /**
     * Defines the {@link JerseyClientProxyFactory} bean.
     *
     * @return the factory bean
     */
    @Bean
    public JerseyClientProxyFactory jerseyClientProxyFactory() {

        return new JerseyClientProxyFactory();
    }
}
