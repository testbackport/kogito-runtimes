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
package org.kie.kogito.addons.quarkus.k8s.config;

import org.junit.jupiter.api.Test;
import org.kie.kogito.addons.quarkus.k8s.KubernetesProtocol;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class KubernetesProtocolTest {

    @Test
    void parseVanillaKubernetes() {
        assertThat(KubernetesProtocol.from("kubernetes"))
                .isEqualTo(KubernetesProtocol.VANILLA_KUBERNETES);
    }

    @Test
    void parseOpenShift() {
        assertThat(KubernetesProtocol.from("openshift"))
                .isEqualTo(KubernetesProtocol.OPENSHIFT);
    }

    @Test
    void parseKnative() {
        assertThat(KubernetesProtocol.from("knative"))
                .isEqualTo(KubernetesProtocol.KNATIVE);
    }

    @Test
    void parseInvalidProtocol() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> KubernetesProtocol.from("nonexistent_protocol"))
                .withMessage("The provided protocol [nonexistent_protocol] is not " +
                        "supported, supported values are 'kubernetes', 'openshift', and 'knative'");
    }
}