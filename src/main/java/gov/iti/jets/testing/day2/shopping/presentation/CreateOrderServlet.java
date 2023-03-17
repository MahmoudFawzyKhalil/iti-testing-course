package gov.iti.jets.testing.day2.shopping.presentation;

import gov.iti.jets.testing.day2.shopping.domain.Order;
import gov.iti.jets.testing.day2.shopping.domain.Product;
import gov.iti.jets.testing.day2.shopping.domain.ShoppingCart;
import gov.iti.jets.testing.day2.shopping.domain.User;
import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.Database;
import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.UserDao;
import gov.iti.jets.testing.day2.shopping.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/create-order")
public class CreateOrderServlet extends HttpServlet {

    private final OrderService orderService = OrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ShoppingCart shoppingCart = SessionAttributes.SHOPPING_CART.get(req);
        Order order = orderService.createOrder(shoppingCart != null ? shoppingCart : createFakeShoppingCart());
        ViewAttributes.CREATED_ORDER.set(req, order);
        Jsps.VIEW_CREATED_ORDER.forward(req, resp);
    }

    private static ShoppingCart createFakeShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart(1L);
        Database.doInTransactionWithoutResult(em -> UserDao.save(
                                                      new User("Mahmoud", "011"), em
                                              )
        );

        shoppingCart.addProduct(new Product("Shampoo", 25_000));

        return shoppingCart;
    }
}
