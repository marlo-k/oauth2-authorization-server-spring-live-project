package de.playground.oauth2_auth_server.config.userdetails;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

// dev only
@Component
public class HardcodedUserDetailsProvider implements UserDetailsProvider {

    @Override
    public List<UserDetails> get() {
        return List.of(User
                .withUsername("alice")
                .password("password")
                .authorities("USER")
                .build());
    }

}
