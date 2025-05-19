package com.example.tokenexchange.resourceserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/api/messages")
public class MessageApi {
    private static final Logger LOG = LoggerFactory.getLogger(MessageApi.class);
    private static final String TARGET_RESOURCE_SERVER_URL = "http://localhost:9092/api/messages";
    private final RestClient restClient;

    public MessageApi(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping
    public String message(JwtAuthenticationToken jwtAuthentication) {
        LOG.info("Called the token exchange resource server with token subject {} and audience {}",
                jwtAuthentication.getToken().getSubject(),
                jwtAuthentication.getToken().getAudience());

        LOG.info("Call target resource server with retrieved token...");
        RestClient.ResponseSpec responseSpec = restClient.get().uri(TARGET_RESOURCE_SERVER_URL)
                .headers(headers -> headers.setBearerAuth(jwtAuthentication.getToken().getTokenValue()))
                .retrieve();
        ResponseEntity<String> responseEntity = responseSpec.toEntity(String.class);
        LOG.info("Successfully called the target resource server with exchanged token");
        return "I am a message from the token exchange resource server with " + responseEntity.getBody();
    }
}
