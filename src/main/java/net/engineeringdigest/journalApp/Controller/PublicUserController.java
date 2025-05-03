package net.engineeringdigest.journalApp.Controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Services.UserDetailsServiceImplement;
import net.engineeringdigest.journalApp.Services.UserServices;
import net.engineeringdigest.journalApp.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicUserController {

    @Autowired
    private UserServices userServices;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImplement userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user){
    userServices.saveUser(user);
    }

    @PostMapping("/log-in")
    public ResponseEntity<String> login(@RequestBody User user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Error",e);
            return new ResponseEntity<>("AUTHENTICATION FAILED INCORRECT ID OR PASSWORD !!!",HttpStatus.BAD_REQUEST);

        }
    }
}

