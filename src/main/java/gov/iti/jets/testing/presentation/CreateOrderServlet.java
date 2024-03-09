package gov.iti.jets.testing.presentation;

import gov.iti.jets.testing.domain.Order;
import gov.iti.jets.testing.domain.Product;
import gov.iti.jets.testing.domain.ShoppingCart;
import gov.iti.jets.testing.domain.User;
import gov.iti.jets.testing.infrastructure.persistence.Database;
import gov.iti.jets.testing.infrastructure.persistence.UserDao;
import gov.iti.jets.testing.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/create-order")
public class CreateOrderServlet extends HttpServlet {

    private final OrderService orderService;

    public CreateOrderServlet() {
        orderService = OrderService.getInstance();
    }

    public CreateOrderServlet(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ShoppingCart shoppingCart = SessionAttributes.SHOPPING_CART.get(req);

        Order order = orderService.createOrder(shoppingCart);

        RequestAttributes.CREATED_ORDER.set(req, order);

        Jsps.VIEW_CREATED_ORDER.forward(req, resp);
    }
}
