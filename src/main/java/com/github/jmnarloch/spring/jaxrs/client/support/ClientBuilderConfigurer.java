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

import org.springframework.util.Assert;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.ClientBuilder;
import java.security.KeyStore;

/**
 * The {@link ClientBuilder} configurer. Exposes most of the methods defined by {@link ClientBuilder}
 * allowing to customize it's behaviour. It's possible to get access to underlying {@link ClientBuilder} instance
 * through the {@link #unwrap(Class)} method.
 *
 * @author Jakub Narloch
 */
public class ClientBuilderConfigurer {

    /**
     * The builder holder.
     */
    private final ClientBuilderHolder<?> builderHolder;

    /**
     * Creates new instance of {@link ClientBuilderConfigurer} with specified client builder.
     * @param builderHolder the client builder
     */
    public ClientBuilderConfigurer(ClientBuilderHolder<?> builderHolder) {

        this.builderHolder = builderHolder;
    }

    /**
     * Registers the hostname verifier.
     *
     * @param hostnameVerifier the hostname verifier
     * @return the builder instance
     */
    public ClientBuilderConfigurer hostnameVerifier(HostnameVerifier hostnameVerifier) {

        builder().hostnameVerifier(hostnameVerifier);
        return this;
    }

    /**
     * Registers the key store to be used.
     *
     * @param keyStore the key store
     * @param password the password
     * @return the builder instance
     */
    public ClientBuilderConfigurer keyStore(KeyStore keyStore, char[] password) {

        builder().keyStore(keyStore, password);
        return this;
    }

    /**
     * Registers the key store to be used.
     *
     * @param keyStore the key store
     * @param password the password
     * @return the builder instance
     */
    public ClientBuilderConfigurer keyStore(KeyStore keyStore, String password) {

        builder().keyStore(keyStore, password);
        return this;
    }

    /**
     * Registers the ssl context.
     *
     * @param sslContext the ssl context
     * @return the builder instance
     */
    public ClientBuilderConfigurer sslContext(SSLContext sslContext) {

        builder().sslContext(sslContext);
        return this;
    }

    /**
     * Registers the trust store.
     *
     * @param trustStore the trust store
     * @return the builder instance
     */
    public ClientBuilderConfigurer trustStore(KeyStore trustStore) {

        builder().trustStore(trustStore);
        return this;
    }

    /**
     * Unwraps the underlying {@link ClientBuilder} instance
     *
     * @param clientBuilderClass the client builder class
     * @param <T>                the type
     * @return the client builder
     * @throws IllegalArgumentException if {@code clientBuilderClass} is {@code null} or the passed class does not
     *                                  match the actual builder
     */
    @SuppressWarnings("unchecked")
    public <T extends ClientBuilder> T unwrap(Class<T> clientBuilderClass) {
        Assert.notNull(clientBuilderClass, "Client builder class is required");

        final ClientBuilder builder = builder();
        if (!clientBuilderClass.isAssignableFrom(builder.getClass())) {
            throw new IllegalStateException(String.format("Could not cast %s into %s", builder.getClass().getName(),
                    clientBuilderClass.getName()));
        }
        return (T) builder;
    }

    /**
     * Retrieves the builder intance.
     *
     * @return the builder intance
     */
    private ClientBuilder builder() {

        return builderHolder.getClientBuilder();
    }
}
