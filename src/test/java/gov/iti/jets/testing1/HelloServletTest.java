package gov.iti.jets.testing1;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.client.WireMockBuilder;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HelloServletTest {

    @Test
    void test() {
        WireMockServer s = new WireMockServer(
                new WireMockConfiguration().port( 0 )
        );

        s.start();
        System.out.println(s.port());
    }
}