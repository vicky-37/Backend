package net.engineeringdigest.journalApp.ReposTest;

import net.engineeringdigest.journalApp.Repos.UserRepoCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepoCriteriaTest {

    @Autowired
    private UserRepoCriteria userRepoCriteria;

    @Test
    public void Test(){
        userRepoCriteria.getSentiAnalys();
    }
}
