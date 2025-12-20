package org.example.wiremockproxyprogrammatic;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ProgrammaticProxyTest {
    @RegisterExtension
    static WireMockExtension wm = WireMockExtension.newInstance()
            .proxyMode(true)
            .build();

    CloseableHttpClient client;

    @BeforeEach
    void init() {
        client = HttpClientBuilder.create()
                .useSystemProperties() // This must be enabled for auto proxy config
                .build();
    }

    @Test
    void configures_jvm_proxy_and_enables_browser_proxying() throws Exception {
        wm.stubFor(get("/things")
                .withHost(equalTo("one.my.domain"))
                .willReturn(ok("1")));

        wm.stubFor(get("/things")
                .withHost(equalTo("two.my.domain"))
                .willReturn(ok("2")));

        assertThat(getContent("http://one.my.domain/things"), is("1"));
        assertThat(getContent("http://two.my.domain/things"), is("2"));
    }

    private String getContent(String url) throws Exception {
        try (CloseableHttpResponse response = client.execute(new HttpGet(url))) {
            return EntityUtils.toString(response.getEntity());
        }
    }
}