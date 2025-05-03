package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Repos.UserRepo;
import net.engineeringdigest.journalApp.Services.UserServices;

import net.engineeringdigest.journalApp.Services.WeatherService;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User dbuser = userServices.findByUsername(userName);
            dbuser.setUsername(user.getUsername());
            dbuser.setPassword(user.getPassword());
            userServices.finalSave(dbuser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepo.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<?> greetings() {// method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("mumbai");
        String greetings = "";
        if (weatherResponse != null) {
        greetings = "Weather feels like: " + weatherResponse.getCurrent().getFeelslikeC();
        }
            return new ResponseEntity<>("HIIII!!!!" + authentication.getName() + greetings, HttpStatus.OK);
    }




}
