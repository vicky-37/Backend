package net.engineeringdigest.journalApp.Services;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Repos.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryServices {
    @Autowired
    private JournalEntryRepo JournalEntryRepo;

    @Autowired
    private UserServices userServices;

    @Transactional
    public void save(JournalEntry journalEntry, String userName) {
        try {
            User user = userServices.findByUsername(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = JournalEntryRepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            userServices.finalSave(user);
        } catch (Exception e) {
            log.error("Error Occured:", e);
            throw new RuntimeException("An error occured:", e);
        }
    }

    public void save(JournalEntry journalEntry) {
        JournalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAll() {

        return JournalEntryRepo.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {

        return JournalEntryRepo.findById(id);
    }

    public boolean deleteById(ObjectId id, String userName) {
        boolean result = false;
        try {
            User user = userServices.findByUsername(userName);
            result = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (result) {
                userServices.finalSave(user);
                JournalEntryRepo.deleteById(id);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while deleting the entry");
        }
        return result;
    }
}
