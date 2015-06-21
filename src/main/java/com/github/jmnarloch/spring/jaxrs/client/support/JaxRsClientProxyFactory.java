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

/**
 * Abstracts all the details of JAX-RS client proxy creation.
 *
 * The concrete implementation of this interface will be responsible for handling all the specific of creation
 * of the proxy classes.
 *
 * @author Jakub Narloch
 */
public interface JaxRsClientProxyFactory {

    /**
     * Creates the proxy class out of specific interface. The proxy is being created for the service interface.
     *
     * @param serviceClass the service class
     * @param serviceUrl the service url
     * @param providers the providers
     * @param <T> the service type
     * @return the created proxy
     */
    <T> T createClientProxy(Class<T> serviceClass, String serviceUrl, Class<?>[] providers);
}
