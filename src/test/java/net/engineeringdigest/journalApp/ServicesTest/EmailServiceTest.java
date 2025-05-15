package net.engineeringdigest.journalApp.ServicesTest;

import net.engineeringdigest.journalApp.Services.EmailService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Disabled
    @Test
    public void testSendMail() {
        emailService.sendEmail("duchess348@slmail.me",
                "Testing Java mail sender",
                "Hi, aap kaise hain ?");
    }
}
