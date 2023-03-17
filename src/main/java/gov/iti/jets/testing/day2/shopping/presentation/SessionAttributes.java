package gov.iti.jets.testing.day2.shopping.presentation;

import jakarta.servlet.http.HttpServletRequest;

@SuppressWarnings("unchecked")
public enum SessionAttributes {

    SHOPPING_CART;

    public void set(HttpServletRequest req, Object value) {
        req.setAttribute(this.name(), value);
    }

    public <T> T get(HttpServletRequest req) {
        return (T) req.getSession().getAttribute(this.name());
    }
}
