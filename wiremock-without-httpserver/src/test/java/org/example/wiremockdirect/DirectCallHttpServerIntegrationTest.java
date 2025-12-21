/*
 * Copyright (C) 2021-2022 Thomas Akehurst
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.example.wiremockdirect;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.http.RequestMethod.GET;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.example.wiremockdirect.MockRequest.mockRequest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.direct.DirectCallHttpServer;
import com.github.tomakehurst.wiremock.direct.DirectCallHttpServerFactory;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.http.Response;
import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.Test;

class DirectCallHttpServerIntegrationTest {

    @Test
    void exampleUsage() {
        DirectCallHttpServerFactory factory = new DirectCallHttpServerFactory();
        WireMockServer wm = new WireMockServer(wireMockConfig().httpServerFactory(factory));
        wm.start(); // no-op

        DirectCallHttpServer server = factory.getHttpServer();

        MockRequest req = mockRequest().method(GET);
        // The location of the mappings folder is not configured. Be default,
        // src/test/resources in the filesystem is the root folder of the mappings folder.
        // nothing is there. so 404 error happens.

        Response response = server.stubRequest(req);
        assertEquals(404, response.getStatus());
    }

    @Test
    void withDelay() {
        DirectCallHttpServerFactory factory = new DirectCallHttpServerFactory();
        WireMockServer wm =
                new WireMockServer(
                        wireMockConfig()
                                .usingFilesUnderClasspath("wiremock-root")
                                .httpServerFactory(factory));
        wm.start(); // no-op

        DirectCallHttpServer server = factory.getHttpServer();

        MockRequest mockRequest = mockRequest().url("/slow-response").method(GET);

        Stopwatch stopwatch = Stopwatch.createStarted();
        Response response = server.stubRequest(mockRequest);
        stopwatch.stop();

        assertEquals(200, response.getStatus());
        assertThat(stopwatch.elapsed(MILLISECONDS), greaterThanOrEqualTo(499L));
    }

    @Test
    void withFileBody() {
        DirectCallHttpServerFactory factory = new DirectCallHttpServerFactory();
        WireMockServer wm =
                new WireMockServer(
                        wireMockConfig()
                                .usingFilesUnderClasspath("wiremock-root")
                                .httpServerFactory(factory));
        wm.start(); // no-op

        DirectCallHttpServer server = factory.getHttpServer();

        MockRequest mockRequest = mockRequest().url("/test").method(GET);
        Response response = server.stubRequest(mockRequest);
        assertEquals("Test World!", response.getBodyAsString());
    }
}