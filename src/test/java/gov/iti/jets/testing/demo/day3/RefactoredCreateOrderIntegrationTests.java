package gov.iti.jets.testing.demo.day3;

import gov.iti.jets.testing.day2.shopping.domain.Order;
import gov.iti.jets.testing.day2.shopping.domain.Product;
import gov.iti.jets.testing.day2.shopping.domain.ShoppingCart;
import gov.iti.jets.testing.day2.shopping.domain.User;
import gov.iti.jets.testing.day2.shopping.infrastructure.gateway.SmsGateway;
import gov.iti.jets.testing.day2.shopping.presentation.CreateOrderServlet;
import gov.iti.jets.testing.day2.shopping.presentation.Jsps;
import gov.iti.jets.testing.day2.shopping.presentation.RequestAttributes;
import gov.iti.jets.testing.day2.shopping.presentation.SessionAttributes;
import gov.iti.jets.testing.day2.shopping.service.OrderService;
import gov.iti.jets.testing.demo.day2.extensions.DatabaseTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

@DatabaseTest
@Tag("integration")
class RefactoredCreateOrderIntegrationTests extends AbstractServletTest {

    @Test
    void Order_is_created_servlet_refactored() {
        // Arrange
        SmsGatewayMock smsGatewayMock = new SmsGatewayMock(); // 1
        OrderService orderService = new OrderService( smsGatewayMock );
        CreateOrderServlet createOrderServlet = new CreateOrderServlet( orderService );

        User user = Users.save( Users.randomUser() );
        List<Product> products = Products.randomProducts();
        ShoppingCart shoppingCart = ShoppingCarts.randomShoppingCart( user.getId(), products );

        SessionAttributes.SHOPPING_CART.set( mockRequest, shoppingCart );

        // Act
        doGet( createOrderServlet );

        // Assert
        Order createdOrder = RequestAttributes.CREATED_ORDER.get( mockRequest );
        Assertions.assertThat( Orders.findAll() )
                .hasSize( 1 )
                .first()
                .usingRecursiveComparison()
                .isEqualTo( createdOrder );

        smsGatewayMock.verifySmsSent( user.getPhoneNumber(), createdOrder.createOrderCreatedSmsMessage() );

        assertForwardedTo( Jsps.VIEW_CREATED_ORDER.getPath() );
    }

    @Test
    void Order_is_created_servlet_wiremock() {
        try (var mockSmsServer = new MockSmsServer()) {
            // Arrange
            SmsGateway smsGateway = new SmsGateway( mockSmsServer.getUrl() );
            OrderService orderService = new OrderService( smsGateway );
            CreateOrderServlet createOrderServlet = new CreateOrderServlet( orderService );

            User user = Users.save( Users.randomUser() );
            List<Product> products = Products.randomProducts();
            ShoppingCart shoppingCart = ShoppingCarts.randomShoppingCart( user.getId(), products );
            products.forEach( shoppingCart::addProduct );

            SessionAttributes.SHOPPING_CART.set( mockRequest, shoppingCart );

            // Act
            doGet( createOrderServlet );

            // Assert
            Order createdOrder = RequestAttributes.CREATED_ORDER.get( mockRequest );
            Assertions.assertThat( Orders.findAll() )
                    .hasSize( 1 )
                    .first()
                    .usingRecursiveComparison()
                    .isEqualTo( createdOrder );

            assertForwardedTo( Jsps.VIEW_CREATED_ORDER.getPath() );
            mockSmsServer.verifySmsSent( user.getPhoneNumber(), createdOrder.createOrderCreatedSmsMessage() );
        }
    }

}
