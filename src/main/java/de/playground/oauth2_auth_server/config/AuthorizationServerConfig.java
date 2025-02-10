package de.playground.oauth2_auth_server.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import de.playground.oauth2_auth_server.config.jwk.JwkProvider;
import de.playground.oauth2_auth_server.config.registeredclient.RegisteredClientProvider;
import de.playground.oauth2_auth_server.config.userdetails.UserDetailsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;


@Configuration
public class AuthorizationServerConfig {


    // applies a minimal default config and registers a SecurityFilterChain
    // all default protocol endpoints are configured
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();
        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());

        http.exceptionHandling(c -> c.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")));

        return http.build();
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public RegisteredClientRepository inMemoryRegisteredClientRepo(RegisteredClientProvider registeredClientProvider) {
        return new InMemoryRegisteredClientRepository(registeredClientProvider.get());
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(JwkProvider jwkProvider) {
        return new ImmutableJWKSet<>(new JWKSet(jwkProvider.get()));
    }

    @Bean
    public UserDetailsService inMemoryUserDetailsService(UserDetailsProvider userDetailsProvider) {
        return new InMemoryUserDetailsManager(userDetailsProvider.get());
    }

    @Bean
    public PasswordEncoder noop() {
        return NoOpPasswordEncoder.getInstance();
    }
}
