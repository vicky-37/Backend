package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserServices userServices;

    @GetMapping("/full-access")
    public ResponseEntity<?> getAll(){
        List<User> all = userServices.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/Create-Admin-Users")
    public ResponseEntity<?> CreateAdminUsers(@RequestBody User user){
        userServices.saveAdmin(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
