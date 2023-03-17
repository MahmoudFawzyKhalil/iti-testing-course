package gov.iti.jets.testing.day2.shopping.presentation;

import jakarta.servlet.http.HttpServletRequest;

public enum ViewAttributes {
    CREATED_ORDER;

    public void set(HttpServletRequest req, Object value) {
        req.setAttribute(this.name(), value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(HttpServletRequest req) {
        return (T) req.getAttribute(this.name());
    }
}
