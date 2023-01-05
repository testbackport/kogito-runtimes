/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates.
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
package org.kie.kogito.quarkus.workflows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

@QuarkusIntegrationTest
class ForEachRestIT {

    @Test
    void testForEachRest() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"workflowdata\" : {\"input\" : [2,4,6,8,10], \"divisor\": 2}}").when()
                .post("/forEachCustomType")
                .then()
                .statusCode(201)
                .body("workflowdata.output", is(Arrays.asList(2, 3, 4, 5, 6)))
                .body("workflowdata.response", nullValue());
    }

    @Test
    void testForEachSubflow() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"numbers\" : [1,2,3,4,5], \"constant\": 2}}").when()
                .post("/foreach_parent")
                .then()
                .statusCode(201)
                .body("workflowdata.products", is(Arrays.asList(2, 4, 6, 8, 10)));
    }
}
