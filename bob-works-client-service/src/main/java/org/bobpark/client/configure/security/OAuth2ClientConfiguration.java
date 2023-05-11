package org.bobpark.client.configure.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.http.Cookie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import org.bobpark.client.configure.properties.AppProperties;
import org.bobpark.core.exception.ServiceRuntimeException;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class OAuth2ClientConfiguration {

    private static final Pattern ISSUER_DOMAIN_PATTERN = Pattern.compile(
        "(?<schema>http[s]?):\\/\\/(?<domain>[\\w.\\d]+)([:](?<port>\\d+))?");

    private final AppProperties properties;

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2AuthorizedClientRepository authorizedClientRepository;

    @Value("${spring.security.oauth2.client.provider.bob-works.issuer-uri}")
    private String issuerUri;

    @Bean
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
            requests ->
                requests.anyRequest().authenticated());

        http.oauth2Login(oauth2login ->
            oauth2login
                .failureHandler((request, response, exception) -> {
                    log.error("Authorized Exception - {}", exception.getMessage(), exception);
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
                })
                .successHandler(successHandler()));

        http.exceptionHandling(exception ->
            exception.authenticationEntryPoint((request, response, authException) ->
                response.sendError(HttpStatus.UNAUTHORIZED.value(), authException.getMessage())
            ));

        http.oauth2Client(Customizer.withDefaults());

        http.logout(logout ->
            logout
                .addLogoutHandler(logoutHandler())
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl(properties.getRedirectUrl())
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID"));

        http.csrf().disable();

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
        return (request, response, authentication) -> response.sendRedirect(properties.getRedirectUrl());
    }

    private LogoutHandler logoutHandler() {
        return (request, response, authentication) ->
            authorizedClientRepository.removeAuthorizedClient("bob-works", authentication, request, response);

    }

    private String extractDomain(String url) {
        Matcher matcher = ISSUER_DOMAIN_PATTERN.matcher(url);

        if (!matcher.find()) {
            throw new ServiceRuntimeException("Invalid issuer url.");
        }

        return matcher.group("domain");
    }

}
