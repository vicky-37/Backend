package net.engineeringdigest.journalApp.Repos;

import net.engineeringdigest.journalApp.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserRepoCriteria {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getSentiAnalys() {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,6}$"));
        query.addCriteria(Criteria.where("SentimentAnalysis").is(true));
        return mongoTemplate.find(query, User.class);

    }
}
