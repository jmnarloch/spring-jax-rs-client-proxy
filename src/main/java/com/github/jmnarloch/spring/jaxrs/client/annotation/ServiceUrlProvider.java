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
package com.github.jmnarloch.spring.jaxrs.client.annotation;

/**
 * The service url provider. Can be registered through {@link EnableJaxRsClient} annotation in order
 * to delay the evaluation of remote service url. It can be used in all cases when the url need to be retrieved in
 * programmatic way at runtime.
 *
 * @author Jakub Narloch
 */
public interface ServiceUrlProvider {

    /**
     * Retrieves the service url.
     *
     * @return the service url
     */
    String getServiceUrl();
}
