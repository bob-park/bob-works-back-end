package org.bobpark.client.configure.feign;

import static com.google.common.net.HttpHeaders.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.Maps;

import feign.Logger.Level;
import feign.RequestInterceptor;

@RequiredArgsConstructor
@Configuration
public class FeignConfiguration {

    private static final String AUTHORIZED_CLIENT_NAME = "bob-works";

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
        return requestTemplate -> requestTemplate.headers(getRequestHeaders());
    }

    private Map<String, Collection<String>> getRequestHeaders() {

        Map<String, Collection<String>> headers = Maps.newHashMap();

        OAuth2AuthenticationToken authentication =
            (OAuth2AuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();

        if (requestAttributes != null && authentication != null) {

            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

            OAuth2AuthorizedClient client =
                authorizedClientService.loadAuthorizedClient(AUTHORIZED_CLIENT_NAME, authentication.getName());

            String authorizationHeader = "Bearer "+ client.getAccessToken().getTokenValue();

            headers.put(X_FORWARDED_FOR, Collections.singletonList(request.getHeader(X_FORWARDED_FOR)));
            headers.put(AUTHORIZATION, Collections.singletonList(authorizationHeader));
        }

        headers.put(CONTENT_TYPE, Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
        headers.put(ACCEPT, Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));

        return headers;
    }
}
