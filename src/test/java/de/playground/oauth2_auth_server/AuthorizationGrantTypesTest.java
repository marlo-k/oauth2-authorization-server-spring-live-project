package de.playground.oauth2_auth_server;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationGrantTypesTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void authorizationCodeGrant() throws Exception {
        mockMvc
                .perform(post("/oauth2/token")
                        .with(httpBasic("client", "secret"))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("client", "client")
                        .param("code", getCode())
                        .param("redirect_uri", "https://www.manning.com/authorized")
                        .param("grant_type", "authorization_code"))
                .andDo(print());
    }

    @Test
    void clientCredentialsGrant() throws Exception {
        mockMvc
                .perform(post("/oauth2/token")
                        .with(httpBasic("client", "secret"))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("client_id", "client")
                        .param("client_secret", "secret")
                        .param("redirect_uri", "https://www.manning.com/authorized")
                        .param("grant_type", "client_credentials"))
                .andDo(print());
    }

    // password grant is not possible anymore
//    @Test
//    void passwordGrant() throws Exception {
//        mockMvc
//                .perform(post("/oauth2/token")
//                        .with(httpBasic("client", "secret"))
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                        .param("client_id", "client")
//                        .param("username", "user")
//                        .param("password", "password")
//                        .param("redirect_uri", "https://www.manning.com/authorized")
//                        .param("grant_type", "password"))
//                .andDo(print());
//    }



    private String getCode() throws Exception {
        String redirectedUrl = mockMvc
                .perform(get("/oauth2/authorize")
                        .with(user("user"))
                        .queryParam("response_type", "code")
                        .queryParam("client_id", "client")
                        .queryParam("scope", "custom")
                        .queryParam("redirect_uri", "https://www.manning.com/authorized"))
                .andReturn()
                .getResponse()
                .getRedirectedUrl();

        return UriComponentsBuilder.fromUriString(redirectedUrl).build().getQueryParams().get("code").getFirst();
    }

}
