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
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.ws.rs.Path;
import java.util.Set;

/**
 * A class path scanner that scans the configured the base packages searching for JAX-RS {@link @Path}
 * annotated interfaces.
 *
 * @author Jakub Narloch
 */
class JaxRsClientClassPathScanner extends ClassPathBeanDefinitionScanner {

    /**
     * The service url.
     */
    private String serviceUrl;

    /**
     * The service url provider.
     */
    private Class<? extends ServiceUrlProvider> serviceUrlProvider;

    /**
     * The provider classes.
     */
    private Class<?>[] providers;

    /**
     * Creates new instance of {@link JaxRsClientClassPathScanner} class.
     *
     * @param registry the bean definition registry
     */
    public JaxRsClientClassPathScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
        registerFilters();
    }

    /**
     * Sets the service url.
     *
     * @param serviceUrl service url
     */
    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    /**
     * Sets the service url provider.
     *
     * @param serviceUrlProvider the service url provider
     */
    public void setServiceUrlProvider(Class<? extends ServiceUrlProvider> serviceUrlProvider) {
        this.serviceUrlProvider = serviceUrlProvider;
    }

    /**
     * Sets the providers.
     *
     * @param providers the providers
     */
    public void setProviders(Class<?>[] providers) {
        this.providers = providers;
    }

    /**
     * Registers the filters.
     */
    protected void registerFilters() {

        // registers the JAX-RS Path annotation
        addIncludeFilter(new AnnotationTypeFilter(Path.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {

        final Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (!beanDefinitions.isEmpty()) {
            processBeanDefinitions(beanDefinitions);
        }

        return beanDefinitions;
    }

    /**
     * Process the bean definitions.
     *
     * @param beanDefinitions the bean definitions
     */
    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {

        for (BeanDefinitionHolder beanDefinition : beanDefinitions) {
            GenericBeanDefinition definition = (GenericBeanDefinition) beanDefinition.getBeanDefinition();

            final String serviceClassName = definition.getBeanClassName();

            definition.setBeanClass(JaxRsClientProxyFactoryBean.class);
            definition.getPropertyValues().add("serviceClass", serviceClassName);
            definition.getPropertyValues().add("serviceUrl", serviceUrl);
            definition.getPropertyValues().add("serviceUrlProvider", serviceUrlProvider);
            definition.getPropertyValues().add("providers", providers);
        }
    }
}
