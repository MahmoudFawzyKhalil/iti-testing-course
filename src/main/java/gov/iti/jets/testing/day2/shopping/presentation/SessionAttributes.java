package gov.iti.jets.testing.day2.shopping.presentation;

import jakarta.servlet.http.HttpServletRequest;

public enum SessionAttributes {

    SHOPPING_CART,
    LOGGED_IN_USER;


    public void set(HttpServletRequest req, Object value) {
        req.getSession().setAttribute(this.name(), value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(HttpServletRequest req) {
        return (T) req.getSession().getAttribute(this.name());
    }
}
