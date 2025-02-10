package de.playground.oauth2_auth_server.config.registeredclient;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.List;

public interface RegisteredClientProvider {

    List<RegisteredClient> get();
}
