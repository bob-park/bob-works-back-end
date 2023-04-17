package org.bobparks.client.configure.security;

import java.time.ZoneId;

import jakarta.servlet.http.Cookie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class OAuth2ClientConfiguration {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2AuthorizedClientRepository authorizedClientRepository;

    @Bean
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
            requests ->
                requests.anyRequest().authenticated());

        http.oauth2Login(oauth2login ->
            oauth2login
                .failureHandler((request, response, exception) -> {
                    log.error("Authorized Exception - {}", exception.getMessage(), exception);
                })
                .successHandler(successHandler()));

        http.oauth2Client();

        http.logout(logout ->
            logout
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID"));

        return http.build();
    }

    @Bean
    public DefaultOAuth2AuthorizedClientManager authorizedClientManager() {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
            OAuth2AuthorizedClientProviderBuilder.builder()
                .authorizationCode()
                .clientCredentials()
                .refreshToken()
                .build();

        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
            new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {

            OAuth2AuthorizedClient client =
                authorizedClientRepository.loadAuthorizedClient("bob-works", authentication, request);

            Cookie cookie = new Cookie("accessToken", client.getAccessToken().getTokenValue());

            long expired = client.getAccessToken().getIssuedAt().toEpochMilli() - System.currentTimeMillis();

            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge((int)expired);

            response.addCookie(cookie);

            response.sendRedirect("http://127.0.0.1:3000");
        };
    }

}
