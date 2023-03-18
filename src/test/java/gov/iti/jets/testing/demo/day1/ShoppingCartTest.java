package gov.iti.jets.testing.demo.day1;

import gov.iti.jets.testing.day1.MessageFormatter;
import gov.iti.jets.testing.day1.shopping.Product1;
import gov.iti.jets.testing.day1.shopping.ShoppingCart1;
import gov.iti.jets.testing.day2.shopping.domain.Product;
import gov.iti.jets.testing.day2.shopping.domain.ShoppingCart;
import jakarta.inject.Singleton;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Signature;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@ExtendWith(MockitoExtension.class)
class ShoppingCartTest {

    @Test
    void Same_product_added_twice_increments_count() {
        // Arrange
        ShoppingCart1 shoppingCart = new ShoppingCart1();

        // Act
        Product1 shampoo = new Product1( "Shampoo", 10 );
        shoppingCart.addProduct( shampoo );
        shoppingCart.addProduct( shampoo );

        // Assert
        Integer count = shoppingCart.getCount( shampoo );

        Assertions.assertThat( count )
                .isEqualTo( 2 );
    }

    @Test
    void Calculates_total() {
        // Arrange
        ShoppingCart shoppingCart = new ShoppingCart( 1L );

        shoppingCart.addProduct( new Product( "panadol", 10_000 ) );
        shoppingCart.addProduct( new Product( "pepsi", 5_000 ) );

        // Act
        int total = shoppingCart.calculateTotal();

        // Assert
        Assertions.assertThat( total )
                .isEqualTo( 15_000 );
    }

    @Test
    void Add_product() {
        // Arrange
        ShoppingCart shoppingCart = new ShoppingCart( 1L );

        // Resistance to refactoring
        // Code pollution -> change production code to test it
        // Broke encapsulation -> clients will depend on implementation detail (collection)
        // State verification of implementation detail

        // Act
        Product panadol = new Product( "panadol", 10_000 );
        shoppingCart.addProduct( panadol );

        // Assert
        Map<Product, Integer> productMap =
                shoppingCart.getProductToQuantity();

        Assertions.assertThat( productMap.containsKey( panadol ) )
                .isTrue();
    }


    // Pure function -> mathematical function
    // fn x = x + 1 fx 1 = 2, 2 = 3

    @Test
    void Divide_by_zero() {
//        int result = divide( 5, 0 );

//        Assertions.assertThat( result )
    }

    // No hidden inputs
    // Hidden outputs: either returns an int result, OR throws an exception
    // Vavr
    // Either<Integer, Exception>
    public Optional<Integer> divide( int a, int b ) {
        Integer result = null;

        try {
            result = a / b;
        } catch (ArithmeticException e) {
//            e.printStackTrace();
        }

        return Optional.ofNullable( result );
    }


    @Test
    void List_test() {
        // Arrange
        List<String> list = new ArrayList<>();
        String string = "Mahmoud";

        // Act
        list.add( string );

        // Assert
        Assertions.assertThat( list.get( 0 ) )
                .isEqualTo( string );
    }

    @Test
    void Create_user() throws InterruptedException {
        // User.create is not a pure function, because it has hidden inputs

        MockedStatic<LocalDateTime> localDateTime =
                Mockito.mockStatic( LocalDateTime.class );

        LocalDateTime stubbedTime =
                LocalDateTime.of( 2020, 1, 1, 1, 1 );


        // Testable
        // Dependency injection
        // OrderService() -> ShoppingService, LocalDateTime.now(), X, Y, Z
        // Polymorphism
        // Instantiate OrderService ->
        localDateTime.close();

//        LocalDateTime.now( Clock.fixed() )

        localDateTime.when( LocalDateTime::now )
                .thenReturn( stubbedTime );

        System.out.println( LocalDateTime.now() );

        LocalDateTime creationDate = LocalDateTime.now();
        User user = User.create( "Mahmoud   " );

        Assertions.assertThat( user.name )
                .isEqualTo( "Mahmoud" );

        // False positive
        Assertions.assertThat( user.createdAt )
                .isEqualTo( stubbedTime );
    }

    // Calling another function/method
    static class User {
        private String name;
        private LocalDateTime createdAt;

        private User( String name ) {
            this.name = name;
        }

        public static User create( String name ) {
            User user = new User( name.strip() );
            user.createdAt = LocalDateTime.now();
            return user;
        }
    }

    @Test
    void Add_message() {
        // 1. Resistance to refactoring [bad] -> OVERSPECIFICATION
        // 2. Protection against regressions
        // 3. Maintainability [average]
        // 4. Speed [good]

        // Arrange
        // Collaboration verification
        // MessageFormatter -> A calls B with the right arguments
        // List<String> -> B
        ArrayList<String> listMock = Mockito.mock();
        MessageFormatter messageFormatter = new MessageFormatter( listMock );

        // Act
        messageFormatter.addMessage( "Hello" );

        // Assert
        // Make sure A calls B's add method with the string "Hello"
        Mockito.verify( listMock ).add( "Hello" );
        // ALWAYS verify no more interactions after verifying the ones you care about
        Mockito.verifyNoMoreInteractions( listMock );
    }

    @Test
    void Stubbing_test() {
        // Arrange
        List<String> listStub = Mockito.mock();

        // stubbing
        Mockito.when( listStub.get( 10 ) ).thenReturn( "Hello World!" );

        System.out.println( listStub.get( 9 ) );
        System.out.println( listStub.get( 10 ) );

    }

    void Singleton_test() {
        Singleton singleton = new Singleton();
        SingletonUser singletonUser = new SingletonUser(singleton);
    }

    static class SingletonUser {
        private final Singleton singleton;

        public SingletonUser() {
            this.singleton = Singleton.getInstance();
        }

        public SingletonUser( Singleton singleton ) {
            this.singleton = singleton;
        }
    }

    static class Singleton {
        private static final Singleton INSTANCE = new Singleton();

        public static Singleton getInstance() {
            return INSTANCE;
        }
    }
}