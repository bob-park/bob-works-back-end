package org.bobpark.authorizationservice.configure.authorization;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

import org.bobpark.authorizationservice.authorization.client.CustomOAuth2AuthorizationConsentService;
import org.bobpark.authorizationservice.authorization.client.CustomOAuth2AuthorizationService;
import org.bobpark.authorizationservice.authorization.client.CustomRegisteredClientRepository;
import org.bobpark.authorizationservice.domain.authorization.repository.AuthorizationClientRepository;
import org.bobpark.authorizationservice.domain.authorization.repository.AuthorizationClientSessionRepository;
import org.bobpark.authorizationservice.domain.authorization.repository.AuthorizationConsentRepository;
import org.bobpark.authorizationservice.domain.authorization.repository.AuthorizationScopeRepository;
import org.bobpark.authorizationservice.domain.user.repository.UserRepository;

@RequiredArgsConstructor
@Configuration
public class AuthorizationServerConfiguration {

    private final AuthorizationClientRepository clientRepository;
    private final AuthorizationClientSessionRepository clientSessionRepository;
    private final AuthorizationConsentRepository consentRepository;
    private final AuthorizationScopeRepository scopeRepository;
    private final UserRepository userRepository;

    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain authorizationFilterChain(HttpSecurity http) throws Exception {

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

        authorizationServerConfigurer.oidc(Customizer.withDefaults());

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        authorizationServerConfigurer.authorizationEndpoint(
            authorization ->
                authorization.consentPage("/oauth2/consent"));

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
    public RegisteredClientRepository registeredClientRepository() {
        return new CustomRegisteredClientRepository(clientRepository, scopeRepository);
    }

    @Bean
    public OAuth2AuthorizationService authorizationService() {
        return new CustomOAuth2AuthorizationService(registeredClientRepository(), clientRepository,
            clientSessionRepository, userRepository);
    }

    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService() {
        return new CustomOAuth2AuthorizationConsentService(clientRepository, consentRepository);
    }

}
