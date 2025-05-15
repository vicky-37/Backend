package net.engineeringdigest.journalApp.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.DTO.UserDTO;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Services.UserDetailsServiceImplement;
import net.engineeringdigest.journalApp.Services.UserServices;
import net.engineeringdigest.journalApp.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
@Tag(name = "User Login / Sign-Up")
public class PublicUserController {

    @Autowired
    private UserServices userServices;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImplement userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck() {
        log.info("Health is ok !");
        return "Ok";
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody UserDTO user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setSentimentAnalysis(user.getSentimentAnalysis());
        userServices.saveUser(newUser);
    }

    @PostMapping("/log-in")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error", e);
            return new ResponseEntity<>("AUTHENTICATION FAILED INCORRECT ID OR PASSWORD !!!", HttpStatus.BAD_REQUEST);

        }
    }
}
