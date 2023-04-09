package org.bobpark.authorizationservice.configure.authorization;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

import org.bobpark.authorizationservice.authorization.client.JpaOAuth2AuthorizationService;
import org.bobpark.authorizationservice.authorization.client.JpaRegisteredClientRepository;
import org.bobpark.authorizationservice.domain.authorization.repository.AuthorizationClientRepository;
import org.bobpark.authorizationservice.domain.authorization.repository.AuthorizationClientSessionRepository;
import org.bobpark.authorizationservice.domain.authorization.repository.AuthorizationScopeRepository;

@RequiredArgsConstructor
@Configuration
public class AuthorizationServerConfiguration {

    private final AuthorizationClientRepository clientRepository;
    private final AuthorizationClientSessionRepository clientSessionRepository;
    private final AuthorizationScopeRepository scopeRepository;

    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain authorizationFilterChain(HttpSecurity http) throws Exception {

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

        authorizationServerConfigurer.oidc(Customizer.withDefaults());

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        // authorizationServerConfigurer.authorizationEndpoint(
        //     authorization ->
        //         authorization.consentPage("/oauth2/consent"));

        http
            .securityMatcher(endpointsMatcher)
            .authorizeHttpRequests(authorize ->
                authorize.anyRequest().authenticated())
            .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
            .apply(authorizationServerConfigurer);

        http.cors(cors -> cors.configurationSource(corsConfigurationSource));

        http.exceptionHandling(
            exception ->
                exception.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")));

        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        return http.build();
    }

    @Bean
    public JpaRegisteredClientRepository registeredClientRepository() {
        return new JpaRegisteredClientRepository(clientRepository, scopeRepository);
    }

    @Bean
    public OAuth2AuthorizationService oAuth2AuthorizationService() {
        return new JpaOAuth2AuthorizationService(registeredClientRepository(), clientRepository,
            clientSessionRepository);
    }

    @Bean
    public OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService() {
        return new InMemoryOAuth2AuthorizationConsentService();
    }

}
