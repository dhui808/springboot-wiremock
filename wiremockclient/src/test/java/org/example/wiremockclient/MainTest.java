package org.example.wiremockclient;


import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;

//Pushing JSON files to a remote WireMock instance
public class MainTest {
    @Test
    public void pushJson() {
        WireMock wireMock = WireMock.create()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .build();

        // The root directory of the WireMock project, under which the mappings and __files directories should be found
        wireMock.loadMappingsFrom("/opt/springboot-wiremock/wiremockclient/wiremock-root");
    }
}