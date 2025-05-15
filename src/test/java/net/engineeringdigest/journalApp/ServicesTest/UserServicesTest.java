package net.engineeringdigest.journalApp.ServicesTest;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Repos.UserRepo;
import net.engineeringdigest.journalApp.Services.UserServices;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // Start Spring Application context No components are made so injection is not
                // possible
public class UserServicesTest {

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepo userRepo;

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,1,3",
            "3,3,6"
    })
    public void set(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }

    @Disabled
    @ParameterizedTest
    @ArgumentsSource(ArgumentSourceProvider.class)
    public void FindByUsername(User user) {
        assertTrue(userServices.saveUser(user));
    }
}
