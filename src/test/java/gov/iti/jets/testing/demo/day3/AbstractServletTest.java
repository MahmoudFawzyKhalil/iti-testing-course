package gov.iti.jets.testing.demo.day3;

import jakarta.servlet.http.HttpServlet;
import org.assertj.core.api.Assertions;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public abstract class AbstractServletTest {

    protected final MockHttpServletRequest mockRequest = new MockHttpServletRequest();
    protected final MockHttpServletResponse mockResponse = new MockHttpServletResponse();

    protected void doGet(HttpServlet servlet) {
        mockRequest.setMethod("GET");
        doService(servlet);
    }

    protected void doPost(HttpServlet servlet) {
        mockRequest.setMethod("POST");
        doService(servlet);
    }

    protected void doPut(HttpServlet servlet) {
        mockRequest.setMethod("PUT");
        doService(servlet);
    }

    private void doService(HttpServlet servlet) {
        try {
            servlet.service(mockRequest, mockResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void assertForwardedTo(String path) {
        Assertions.assertThat(mockResponse.getForwardedUrl())
                .isEqualTo(path);
    }
}
