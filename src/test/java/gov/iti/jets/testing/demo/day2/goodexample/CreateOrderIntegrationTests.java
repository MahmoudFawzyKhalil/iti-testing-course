package gov.iti.jets.testing.demo.day2.goodexample;

import gov.iti.jets.testing.day2.shopping.domain.Order;
import gov.iti.jets.testing.day2.shopping.domain.Product;
import gov.iti.jets.testing.day2.shopping.domain.ShoppingCart;
import gov.iti.jets.testing.day2.shopping.domain.User;
import gov.iti.jets.testing.day2.shopping.infrastructure.gateway.SmsGateway;
import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.Database;
import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.UserDao;
import gov.iti.jets.testing.day2.shopping.presentation.CreateOrderServlet;
import gov.iti.jets.testing.day2.shopping.presentation.RequestAttributes;
import gov.iti.jets.testing.day2.shopping.presentation.SessionAttributes;
import gov.iti.jets.testing.day2.shopping.service.OrderService;
import gov.iti.jets.testing.demo.day2.extensions.DatabaseTest;
import gov.iti.jets.testing.demo.day3.Products;
import jakarta.servlet.ServletException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import java.io.IOException;
import java.util.List;

@DatabaseTest
@Tag("integration")
class CreateOrderIntegrationTests {

    @Test
    void Order_is_created() {
        // Arrange
        SmsGateway smsGatewayMock = Mockito.mock();
        OrderService orderService = new OrderService( smsGatewayMock );

        User user = Users.save( Users.defaultRegularUser() );
        List<Product> products = List.of( new Product( "123", 15_000 ),
                new Product( "343", 10_000 )
        );
        ShoppingCart shoppingCart = createShoppingCart( user.getId(), products );

        // Act
        Order order = orderService.createOrder( shoppingCart );

        // Assert
        // 1. Saved to the database
        List<Order> allOrders = Database.doInTransaction( em -> {
            return em.createQuery( "SELECT o FROM order o JOIN FETCH o.lineItems", Order.class )
                    .getResultList();
        } );

        Assertions.assertThat( allOrders.get( 0 ) )
                .usingRecursiveComparison()
                .isEqualTo( order );

        // 2. SMS service called
        // Delta coverage
        Mockito.verify( smsGatewayMock ).sendSms(
                user.getPhoneNumber(),
                order.createOrderCreatedSmsMessage() );
        Mockito.verifyNoMoreInteractions( smsGatewayMock );
    }

    private static ShoppingCart createShoppingCart( Long userId, List<Product> products ) {
        ShoppingCart shoppingCart = new ShoppingCart( userId );
        products.forEach( shoppingCart::addProduct );
        return shoppingCart;
    }

    @Test
    void Order_is_created_servlet() throws ServletException, IOException {
        // Arrange
        SmsGateway smsGatewayMock = Mockito.mock(); // 1
        OrderService orderService = new OrderService( smsGatewayMock );

        CreateOrderServlet createOrderServlet =
                new CreateOrderServlet( orderService );

        User user = Users.save( Users.defaultRegularUser() );

        ShoppingCart shoppingCart = createShoppingCart( user.getId(), Products.randomProducts());

        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute( SessionAttributes.SHOPPING_CART.name(), shoppingCart );

        var mockRequest = new MockHttpServletRequest();
        mockRequest.setSession( mockSession );
        var mockResponse = new MockHttpServletResponse();
        mockRequest.setMethod( "GET" ); // GET for demo only, should be POST

        // Act
        createOrderServlet.service( mockRequest, mockResponse );

        Order createdOrder = RequestAttributes.CREATED_ORDER.get( mockRequest );
        // Assert
        // 1. Saved to the database
        List<Order> allOrders = Database.doInTransaction( em -> {
            return em.createQuery( "SELECT o FROM order o JOIN FETCH o.lineItems", Order.class )
                    .getResultList();
        } );

        Assertions.assertThat( allOrders.get( 0 ) )
                .usingRecursiveComparison()
                .isEqualTo( createdOrder );

        // 2. SMS service called
        // Delta coverage
        Mockito.verify( smsGatewayMock ).sendSms( // 2
                user.getPhoneNumber(), // 3
                createdOrder.createOrderCreatedSmsMessage() ); // 4
        Mockito.verifyNoMoreInteractions( smsGatewayMock ); // 5

        // 3. Assert forward correct
        Assertions.assertThat( mockResponse.getForwardedUrl() )
                .isEqualTo( "/WEB-INF/view-created-order.jsp" );
    }

    @Test
    void Order_confirmation_SMS_message_sent() {

    }

    static class Users {

        public static final String DEFAULT_USER_NAME = "Mahmoud";
        public static final String DEFAULT_USER_PHONE_NUMBER = "011793535353";

        public static User defaultRegularUser() {
            return User.builder()
                    .name( DEFAULT_USER_NAME )
                    .phoneNumber( DEFAULT_USER_PHONE_NUMBER )
                    .build();
        }

        public static User defaultAdminUser() {
            return defaultRegularUser()
                    .toBuilder()
                    .role( "ADMIN" )
                    .build();
        }

        public static User save( User user ) {
            Database.doInTransactionWithoutResult( em -> UserDao.save(
                    user,
                    em
            ) );
            return user;
        }
    }

}