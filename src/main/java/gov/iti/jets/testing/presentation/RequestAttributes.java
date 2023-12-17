package gov.iti.jets.testing.presentation;

import jakarta.servlet.http.HttpServletRequest;

public enum RequestAttributes {
    CREATED_ORDER;

    public void set(HttpServletRequest req, Object value) {
        req.setAttribute(this.name(), value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(HttpServletRequest req) {
        return (T) req.getAttribute(this.name());
    }
}
