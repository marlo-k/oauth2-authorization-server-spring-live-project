package de.playground.oauth2_auth_server.config.jwk;

import com.nimbusds.jose.jwk.RSAKey;

public interface JwkProvider {

    RSAKey get();
}
