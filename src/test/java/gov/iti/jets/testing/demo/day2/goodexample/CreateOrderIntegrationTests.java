package gov.iti.jets.testing.demo.day2.goodexample;

import gov.iti.jets.testing.day2.shopping.domain.Order;
import gov.iti.jets.testing.day2.shopping.domain.Product;
import gov.iti.jets.testing.day2.shopping.domain.ShoppingCart;
import gov.iti.jets.testing.day2.shopping.domain.User;
import gov.iti.jets.testing.day2.shopping.infrastructure.gateway.SmsGateway;
import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.Database;
import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.OrderDao;
import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.UserDao;
import gov.iti.jets.testing.day2.shopping.presentation.CreateOrderServlet;
import gov.iti.jets.testing.day2.shopping.presentation.Jsps;
import gov.iti.jets.testing.day2.shopping.presentation.RequestAttributes;
import gov.iti.jets.testing.day2.shopping.presentation.SessionAttributes;
import gov.iti.jets.testing.day2.shopping.service.OrderService;
import gov.iti.jets.testing.demo.day2.extensions.DatabaseCleanupExtension;
import gov.iti.jets.testing.demo.day2.extensions.DatabaseTest;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

        User user = new User( "Mahmoud", "0112313123" );
        Database.doInTransactionWithoutResult( em -> UserDao.save(
                user,
                em
        ) );

        ShoppingCart shoppingCart = new ShoppingCart( user.getId() );

        shoppingCart.addProduct( new Product( "123", 15_000 ) );
        shoppingCart.addProduct( new Product( "343", 10_000 ) );

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

    @Test
    void Order_is_created_servlet() throws ServletException, IOException {
        // Arrange
        // Protection against regressions -> amazing
        // Maintainability -> worse than unit tests
        // Quick execution -> worse than units
        // Resistance to refactoring -> amazing
        SmsGateway smsGatewayMock = Mockito.mock();
        OrderService orderService = new OrderService( smsGatewayMock );

        CreateOrderServlet createOrderServlet =
                new CreateOrderServlet( orderService );

        User user = new User( "Mahmoud", "0112313123" );
        Database.doInTransactionWithoutResult( em -> UserDao.save(
                user,
                em
        ) );

        ShoppingCart shoppingCart = new ShoppingCart( user.getId() );

        shoppingCart.addProduct( new Product( "123", 15_000 ) );
        shoppingCart.addProduct( new Product( "343", 10_000 ) );

        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute( SessionAttributes.SHOPPING_CART.name(), shoppingCart );

        var mockRequest = new MockHttpServletRequest();
        mockRequest.setSession( mockSession );
//        SessionAttributes.SHOPPING_CART.set( mockRequest, shoppingCart );


        mockRequest.setMethod( "GET" ); // GET for demo only, should be POST
        var mockResponse = new MockHttpServletResponse();

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
        Mockito.verify( smsGatewayMock ).sendSms(
                user.getPhoneNumber(),
                createdOrder.createOrderCreatedSmsMessage() );
        Mockito.verifyNoMoreInteractions( smsGatewayMock );

        // 3. Assert forward correct
        Assertions.assertThat( mockResponse.getForwardedUrl() )
                .isEqualTo( "/WEB-INF/view-created-order.jsp" );
    }

    @Test
    void Order_confirmation_SMS_message_sent() {

    }

}
