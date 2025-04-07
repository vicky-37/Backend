package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")                  // public endpoint anyone can access it
public class PublicUser {

    @Autowired
    private UserServices userServices;

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
    userServices.saveUser(user);
    }
}

