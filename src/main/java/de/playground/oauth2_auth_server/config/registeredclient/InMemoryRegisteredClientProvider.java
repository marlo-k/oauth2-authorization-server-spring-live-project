package de.playground.oauth2_auth_server.config.registeredclient;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

// dev only
@Component
public class InMemoryRegisteredClientProvider implements RegisteredClientProvider {

    // just hard coded, not flexible at all
    @Override
    public List<RegisteredClient> get() {
        return List.of(
                RegisteredClient
                        .withId(UUID.randomUUID().toString())

                        .clientId("client")
                        .clientSecret("secret")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)

                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                        .authorizationGrantType(AuthorizationGrantType.PASSWORD)     -> not possible anymore
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)

                        .redirectUri("https://www.manning.com/authorized")
                        // may use openid scopes for easier migration to OIDC
                        //.scope(OidcScopes.OPENID)
                        .scope("custom")
                        .build());
    }
}
