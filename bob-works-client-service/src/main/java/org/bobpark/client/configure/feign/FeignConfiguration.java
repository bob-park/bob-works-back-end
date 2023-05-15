package org.bobpark.client.configure.feign;

import static com.google.common.net.HttpHeaders.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;

import feign.Logger.Level;
import feign.RequestInterceptor;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FeignConfiguration {

    private static final String AUTHORIZED_CLIENT_NAME = "bob-works";

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Level feinLogLevel() {
        return Level.FULL;
    }

    @Bean
    public RequestInterceptor customRequestInterceptor() {
        return requestTemplate ->
            requestTemplate.headers(getRequestHeaders());
    }

    private Map<String, Collection<String>> getRequestHeaders() {

        Map<String, Collection<String>> headers = Maps.newHashMap();

        OAuth2AuthenticationToken authentication =
            (OAuth2AuthenticationToken)SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {

            OAuth2AuthorizedClient client =
                authorizedClientService.loadAuthorizedClient(AUTHORIZED_CLIENT_NAME, authentication.getName());

            if (client == null) {
                throw new OAuth2AuthenticationException("unauthorized");
            }

            String authorizationHeader = "Bearer " + client.getAccessToken().getTokenValue();

            headers.put(AUTHORIZATION, Collections.singletonList(authorizationHeader));
        }

        headers.put(ACCEPT, Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));

        return headers;
    }
}
