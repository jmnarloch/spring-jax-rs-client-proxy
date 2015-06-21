/**
 * Copyright (c) 2015 the original author or authors
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jmnarloch.spring.jaxrs.client.cxf;

import com.github.jmnarloch.spring.jaxrs.client.support.JaxRsClientProxyFactory;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * The CXF {@link JaxRsClientProxyFactory} implementation. It delegates the execution to {@link JAXRSClientFactory}
 * create the proxy instances.
 *
 * @author Jakub Narloch
 */
class CxfClientProxyFactory implements JaxRsClientProxyFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T createClientProxy(Class<T> serviceClass, String serviceUrl, Class<?>[] providers) {

        return JAXRSClientFactory.create(serviceUrl, serviceClass, instantiate(providers));
    }

    /**
     * Instantiates the given array of classes and return them as a list. Each of the class need to provide a
     * default constructor.
     *
     * @param classes the classes to instantiate
     * @return the list of instance
     * @throws IllegalStateException if any of the classes could not be instantiated
     */
    private List<?> instantiate(Class<?>[] classes) {
        final List<Object> instances = new LinkedList<Object>();

        for (Class<?> clazz : classes) {
            instances.add(instantiate(clazz));
        }
        return instances;
    }

    /**
     * Instantiates the specific class.
     *
     * @param clazz the class to instantiate
     * @return the class instance
     */
    private Object instantiate(Class<?> clazz) {

        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException("Could not create instance of the provider class", e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Could not create instance of the provider class", e);
        }
    }
}
