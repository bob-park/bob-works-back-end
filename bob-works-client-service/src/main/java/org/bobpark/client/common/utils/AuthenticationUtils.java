package org.bobpark.client.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

public class AuthenticationUtils {

    private static final String AUTHORIZED_CLIENT_NAME = "bob-works";

    private static AuthenticationUtils instance;

    private OAuth2AuthorizedClientService authorizedClientService;

    public static AuthenticationUtils getInstance() {
        if (instance == null) {
            instance = new AuthenticationUtils();
        }

        return instance;
    }

    public String getBearerToken() {

        OAuth2AuthenticationToken authentication =
            (OAuth2AuthenticationToken)SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new OAuth2AuthenticationException("unauthorized");
        }

        OAuth2AuthorizedClient client =
            authorizedClientService.loadAuthorizedClient(AUTHORIZED_CLIENT_NAME, authentication.getName());

        if (client == null) {
            throw new OAuth2AuthenticationException("unauthorized");
        }

        return "Bearer " + client.getAccessToken().getTokenValue();
    }

    @Autowired
    public void setAuthorizedClientService(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }
}
