package net.engineeringdigest.journalApp.APICache;

import net.engineeringdigest.journalApp.Entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.Repos.ConfigJournalAppRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class APICache {

    public enum keys{       //to keep track
        WEATHER_API_URL;
    }
    @Autowired
    private ConfigJournalAppRepo configJournalAppRepo;

    public Map<String,String> appCache;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> findAll = configJournalAppRepo.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : findAll ){
            appCache.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }
}
}
