package net.engineeringdigest.journalApp.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_Journal_App")
@Data
@NoArgsConstructor
public class ConfigJournalAppEntity {

    private String key;
    private String value;
}
