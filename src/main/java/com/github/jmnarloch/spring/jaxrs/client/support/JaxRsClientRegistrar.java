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
package com.github.jmnarloch.spring.jaxrs.client.support;

import com.github.jmnarloch.spring.jaxrs.client.annotation.EnableJaxRsClient;
import com.github.jmnarloch.spring.jaxrs.client.annotation.ServiceUrlProvider;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Registers the JAX-RS client proxy capabilities into the Spring application context.
 *
 * @author Jakub Narloch
 */
public class JaxRsClientRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * The annotation class.
     */
    private static final Class<EnableJaxRsClient> ANNOTATION_CLASS = EnableJaxRsClient.class;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {

        final List<String> basePackages = new ArrayList<String>();
        final Map<String, Object> attributes = annotationMetadata
                .getAnnotationAttributes(ANNOTATION_CLASS.getName(), false);

        final Class<? extends ServiceUrlProvider> serviceUrlProvider = get(attributes, "serviceUrlProvider");
        final String serviceUrl = get(attributes, "serviceUrl");

        addAll(basePackages, attributes, "value");
        addAll(basePackages, attributes, "basePackages");
        addAll(basePackages, toPackageNames((Class[]) attributes.get("basePackageClasses")));

        final JaxRsClientClassPathScanner scanner = new JaxRsClientClassPathScanner(registry);
        scanner.setServiceUrl(serviceUrl);
        scanner.setServiceUrlProvider(serviceUrlProvider);
        scanner.scan(toArray(basePackages));
    }

    /**
     * Retrieves the attribute by it's name
     *
     * @param attributes    the attribute map
     * @param attributeName the attribute name
     * @param <T>           the attribute type
     * @return the attribute value
     */
    @SuppressWarnings("unchecked")
    private static <T> T get(Map<String, Object> attributes, String attributeName) {
        return (T) attributes.get(attributeName);
    }

    /**
     * Retrieves the array of package names for each of the individual class.
     *
     * @param basePackageClasses the base package classes
     * @return the array of package names
     */
    private static String[] toPackageNames(Class[] basePackageClasses) {
        final String[] packages = new String[basePackageClasses.length];
        for (int ind = 0; ind < basePackageClasses.length; ind++) {
            packages[ind] = basePackageClasses[ind].getPackage().getName();
        }
        return packages;
    }

    /**
     * Adds all values associated with the given attribute.
     *
     * @param list          the list of values
     * @param attributes    the attributes map
     * @param attributeName the attribute name
     */
    private static void addAll(List<String> list, Map<String, Object> attributes, String attributeName) {
        addAll(list, (String[]) get(attributes, attributeName));
    }

    /**
     * Ads all values to the value list.
     *
     * @param list   the list of values
     * @param values the values
     */
    private static void addAll(List<String> list, String[] values) {
        list.addAll(Arrays.asList(values));
    }

    /**
     * Converts the list into the array.
     *
     * @param list the list
     * @return the array
     */
    private static String[] toArray(List<String> list) {
        return list.toArray(new String[list.size()]);
    }
}
