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

import com.github.jmnarloch.spring.jaxrs.client.annotation.ServiceUrlProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * An factory bean that is responsible for instation of specific proxy class.
 *
 * @author Jakub Narloch
 */
class JaxRsClientProxyFactoryBean implements ApplicationContextAware, FactoryBean {

    /**
     * The application context.
     */
    private ApplicationContext applicationContext;

    /**
     * The target service class.
     */
    private Class<?> serviceClass;

    /**
     * The service url.
     */
    private String serviceUrl;

    /**
     * The service url provider.
     */
    private Class<? extends ServiceUrlProvider> serviceUrlProvider;

    /**
     * Sets the service class.
     *
     * @param serviceClass the service class
     */
    public void setServiceClass(Class<?> serviceClass) {
        this.serviceClass = serviceClass;
    }

    /**
     * Sets the service url.
     *
     * @param serviceUrl the service url
     */
    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    /**
     * Sets the url provider.
     *
     * @param serviceUrlProvider the service url provider
     */
    public void setServiceUrlProvider(Class<? extends ServiceUrlProvider> serviceUrlProvider) {
        this.serviceUrlProvider = serviceUrlProvider;
    }

    /**
     * Sets the application context.
     *
     * @param applicationContext the application context
     * @throws BeansException if any error occurs
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Retrieves the proxy instance.
     *
     * @return the proxy instance
     * @throws Exception if any error occurs
     */
    @Override
    public Object getObject() throws Exception {

        return createServiceProxy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getObjectType() {
        return serviceClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * Creates the service proxy.
     * <p/>
     * Delegates to registered {@link JaxRsClientProxyFactory} to create the service proxy
     *
     * @return the service proxy
     */
    private Object createServiceProxy() {

        return getProxyFactory().createClientProxy(serviceClass, getServiceUrl());
    }

    /**
     * Retrieves the service url.
     *
     * @return the service url
     */
    private String getServiceUrl() {

        try {
            if (!serviceUrl.isEmpty()) {
                return serviceUrl;
            }

            return applicationContext.getBean(serviceUrlProvider)
                    .getServiceUrl();
        } catch (BeansException e) {
            throw new IllegalStateException("The service url hasn't been specified and " +
                    "no ServiceUrlProvider has been registered in application context.", e);
        }
    }

    /**
     * Retrieves the proxy factory.
     *
     * @return the proxy factory
     */
    private JaxRsClientProxyFactory getProxyFactory() {

        try {

            return applicationContext.getBean(JaxRsClientProxyFactory.class);
        } catch (BeansException e) {
            throw new IllegalStateException(
                    "No JaxRsClientProxyFactory has been registered in the application context. " +
                            "Use one of @EnableCxfClient, @EnableJerseyClient or @EnableRestEasyClient annotations.", e);
        }
    }
}
