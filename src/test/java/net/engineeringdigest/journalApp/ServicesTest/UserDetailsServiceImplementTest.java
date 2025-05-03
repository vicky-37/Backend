package net.engineeringdigest.journalApp.ServicesTest;

import net.bytebuddy.asm.Advice;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Repos.UserRepo;
import net.engineeringdigest.journalApp.Services.UserDetailsServiceImplement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class UserDetailsServiceImplementTest {

    @InjectMocks
    private UserDetailsServiceImplement userDetailsService;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    void ini(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Disabled
    void loadByUsernameTest(){          //"Shyam" |V| or any particular user
        when(userRepo.findByUsername(ArgumentMatchers.anyString())).thenReturn(User.builder().username("ram").password("asdt").roles(new ArrayList<>()).build());
        UserDetails user = userDetailsService.loadUserByUsername("ram");
    }

}

