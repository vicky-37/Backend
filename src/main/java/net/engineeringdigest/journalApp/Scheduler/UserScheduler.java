package net.engineeringdigest.journalApp.Scheduler;

import net.engineeringdigest.journalApp.APICache.APICache;
import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Enums.Sentiment;
import net.engineeringdigest.journalApp.Repos.UserRepoCriteria;
import net.engineeringdigest.journalApp.Services.EmailService;
import net.engineeringdigest.journalApp.Model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private UserRepoCriteria userRepoCriteria;

    @Autowired
    private EmailService emailService;

    @Autowired
    private APICache apiCache;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Scheduled(cron = "0 9 * * * SUN")
    public void fetchAndSendMail() {
        List<User> users = userRepoCriteria.getSentiAnalys();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail())
                        .sentiment("sentiment for 7 days" + mostFrequentSentiment).build();
                try {
                    kafkaTemplate.send("Weekly_Sentiments", sentimentData.getEmail(), sentimentData);
                } catch (Exception e) {
                    emailService.sendEmail(sentimentData.getEmail(), "Sentiments for this week",
                            sentimentData.getSentiment());
                }

            }
        }

    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        apiCache.init();
    }
}
