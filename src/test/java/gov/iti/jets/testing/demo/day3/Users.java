package gov.iti.jets.testing.demo.day3;

import com.github.javafaker.Faker;
import gov.iti.jets.testing.day2.shopping.domain.User;
import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.Database;
import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.UserDao;

public class Users {
    public static User randomUser() {
        return User.builder()
                .name( randomName() )
                .phoneNumber( randomPhoneNumber() )
                .build();
    }

    private static String randomPhoneNumber() {
        return Faker.instance().phoneNumber().cellPhone();
    }

    private static String randomName() {
        return Faker.instance().name().name();
    }

    public static User save( User user ) {
        Database.doInTransactionWithoutResult( em -> UserDao.save(
                user,
                em
        ) );
        return user;
    }
}
