package gov.iti.jets.testing.demo.day3;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.matching.RequestPattern;
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder;
import com.github.tomakehurst.wiremock.verification.FindRequestsResult;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import org.assertj.core.api.Assertions;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockSmsServer implements AutoCloseable {

    public static final String SEND_MESSAGE_PATH = "/send";
    private final WireMockServer server;
    private boolean verified;

    public MockSmsServer() {
        server = new WireMockServer( 0 ); // Random port - 8034
        String responseBody = """
                "status": "SENT"
                """;
        server.stubFor(
                WireMock.post( SEND_MESSAGE_PATH )
                        .willReturn( ok().withBody( responseBody ) )
        );

        server.start();
    }

    public void verifySmsSent( String phoneNumber, String message ) {
        String expectedBody = """
                {
                "phoneNumber": "%s",
                "message": "%s"
                }
                """.formatted( phoneNumber, message );

        server.verify( 1,
                postRequestedFor(
                        urlEqualTo( SEND_MESSAGE_PATH ) )
                        .withRequestBody( equalTo( expectedBody ) ) );

        verified = true;
    }

    @Override
    public void close() {
        if (!verified)
            throw new AssertionError(
                    "No verification happened on this wiremock server" );
        server.shutdown();
    }

    public String getUrl() {
        return server.baseUrl();
    }


}
