/*
 * Copyright 2023 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.kogito.addons.quarkus.knative.serving.customfunctions;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static org.kie.kogito.addons.quarkus.knative.serving.customfunctions.KnativeServerlessWorkflowCustomFunction.CLOUD_EVENT_PROPERTY_NAME;

@ApplicationScoped
class KnativeServiceRequestClientResolver {

    private final CloudEventKnativeServiceRequestClient cloudEventClient;

    private final PlainJsonKnativeServiceRequestClient plainJsonClient;

    @Inject
    KnativeServiceRequestClientResolver(CloudEventKnativeServiceRequestClient cloudEventClient,
            PlainJsonKnativeServiceRequestClient plainJsonClient) {
        this.cloudEventClient = cloudEventClient;
        this.plainJsonClient = plainJsonClient;
    }

    KnativeServiceRequestClient resolve(Map<String, Object> metadata) {
        if (isCloudEvent(metadata)) {
            return cloudEventClient;
        } else {
            return plainJsonClient;
        }
    }

    private static boolean isCloudEvent(Map<String, Object> metadata) {
        return Boolean.parseBoolean(metadata.getOrDefault(CLOUD_EVENT_PROPERTY_NAME, "false").toString());
    }
}
