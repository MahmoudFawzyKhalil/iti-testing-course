package gov.iti.jets.testing.ignore;

import gov.iti.jets.testing.ignore.HelloServlet;
import jakarta.servlet.ServletException;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

class MockingServletTest {

    @Test
    void mockServlet() throws ServletException, IOException {
        // Arrange
        HelloServlet helloServlet = new HelloServlet();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        req.setMethod(HttpMethods.GET);

        // Act
        helloServlet.service(req, res);

        // Assert
        try (var softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(req.getAttribute("betengan"))
                  .isEqualTo(10);

            softly.assertThat(res.getForwardedUrl())
                  .isEqualTo("/WEB-INF/betengan.jsp");
        }
    }

    interface HttpMethods {
        public static final String GET = "GET";
    }
}
