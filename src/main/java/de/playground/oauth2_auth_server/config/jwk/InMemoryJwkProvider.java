package de.playground.oauth2_auth_server.config.jwk;

import com.nimbusds.jose.jwk.RSAKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

// dev only
@Service
@Slf4j
public class InMemoryJwkProvider implements JwkProvider {

    @Override
    public RSAKey get() {
        try {
            return rsaKey();
        } catch (NoSuchAlgorithmException e) {
            // fail fast
            throw new RuntimeException("Could not create in-memory RSA Key");
        }
    }

    public RSAKey rsaKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        log.info("Public key is: {}", publicKey.toString());
        log.info("Private key is: {}", privateKey.toString());


        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }
}
