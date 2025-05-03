package net.engineeringdigest.journalApp.Cron;

import net.engineeringdigest.journalApp.Scheduler.UserScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSchedulerTest {

    @Autowired
    private UserScheduler userScheduler;

    @Test
    public void test(){
        userScheduler.fetchAndSendMail();
    }
}
