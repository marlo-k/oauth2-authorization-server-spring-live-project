package de.playground.oauth2_auth_server.config.userdetails;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserDetailsProvider {

    List<UserDetails> get();
}
