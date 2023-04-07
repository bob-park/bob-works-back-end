package org.bobpark.authorizationservice.authorization.client;

import static org.apache.commons.lang3.math.NumberUtils.*;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.*;
import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationClient;
import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationClientRedirect;
import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationScope;
import org.bobpark.authorizationservice.domain.authorization.repository.AuthorizationClientRepository;
import org.bobpark.authorizationservice.domain.authorization.repository.AuthorizationScopeRepository;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaRegisteredClientRepository implements RegisteredClientRepository {

    private final AuthorizationClientRepository clientRepository;
    private final AuthorizationScopeRepository scopeRepository;

    @Transactional
    @Override
    public void save(RegisteredClient registeredClient) {

        RegisteredClient existingRegisteredClient = findByClientId(registeredClient.getClientId());

        if (existingRegisteredClient != null) {

            AuthorizationClient authorizationClient =
                clientRepository.getByClientId(existingRegisteredClient.getClientId());

            if (authorizationClient != null) {

                authorizationClient.setClientName(existingRegisteredClient.getClientName());
                authorizationClient.setClientSecretExpiresAt(
                    registeredClient.getClientSecretExpiresAt() != null ?
                        LocalDateTime.from(registeredClient.getClientSecretExpiresAt()) : null);
                authorizationClient.setRequiredAuthorizationConsent(
                    registeredClient.getClientSettings().isRequireAuthorizationConsent());
                authorizationClient.setAccessTokenTimeToLive(
                    registeredClient.getTokenSettings().getAccessTokenTimeToLive().toSeconds());

                authorizationClient.setScopes(new ArrayList<>());
                authorizationClient.setRedirectUris(new ArrayList<>());

                Set<String> scopes = existingRegisteredClient.getScopes();

                getScopes().stream()
                    .filter(item -> scopes.contains(item.getScope()))
                    .forEach(authorizationClient::addScope);

                existingRegisteredClient.getRedirectUris()
                    .forEach(authorizationClient::addRedirectUri);

                log.debug("updated registered client. (id={})", authorizationClient.getId());

                return;
            }
        }

        AuthorizationClient saved = clientRepository.save(toAuthorizationClient(registeredClient));

        log.debug("added registered client. (id={})", saved.getId());

    }

    @Override
    public RegisteredClient findById(String id) {

        AuthorizationClient authorizationClient = clientRepository.findById(toLong(id)).orElse(null);

        if (authorizationClient == null) {
            return null;
        }

        return toRegisteredClient(authorizationClient);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {

        AuthorizationClient authorizationClient = clientRepository.getByClientId(clientId);

        if (authorizationClient == null) {
            return null;
        }

        return toRegisteredClient(authorizationClient);
    }

    private AuthorizationClient toAuthorizationClient(RegisteredClient registeredClient) {

        Set<String> scopes = registeredClient.getScopes();
        Set<String> redirectUris = registeredClient.getRedirectUris();

        AuthorizationClient authorizationClient =
            AuthorizationClient.builder()
                .clientId(registeredClient.getClientId())
                .clientSecret(registeredClient.getClientSecret())
                .clientSecretExpiresAt(
                    registeredClient.getClientSecretExpiresAt() != null ?
                        LocalDateTime.from(registeredClient.getClientSecretExpiresAt()) : null)
                .clientName(registeredClient.getClientName())
                .requiredAuthorizationConsent(registeredClient.getClientSettings().isRequireAuthorizationConsent())
                .accessTokenTimeToLive(registeredClient.getTokenSettings().getAccessTokenTimeToLive().toSeconds())
                .build();

        List<AuthorizationScope> authorizationScopes = getScopes();

        for (String scope : scopes) {
            authorizationScopes.stream()
                .filter(item -> item.getScope().equals(scope))
                .findAny()
                .ifPresent(authorizationClient::addScope);
        }

        for (String redirectUri : redirectUris) {
            authorizationClient.addRedirectUri(redirectUri);
        }

        return authorizationClient;
    }

    private List<AuthorizationScope> getScopes() {
        return scopeRepository.findAll();
    }

    private RegisteredClient toRegisteredClient(AuthorizationClient authorizationClient) {

        LocalDateTime clientSecretExpiresAt = authorizationClient.getClientSecretExpiresAt();

        return RegisteredClient.withId(authorizationClient.getId().toString())
            .clientId(authorizationClient.getClientId())
            .clientSecret(authorizationClient.getClientSecret())
            .clientIdIssuedAt(authorizationClient.getClientIssueAt().atZone(ZoneId.systemDefault()).toInstant())
            .clientSecretExpiresAt(
                clientSecretExpiresAt != null ?
                    clientSecretExpiresAt.atZone(ZoneId.systemDefault()).toInstant() : null)
            .clientAuthenticationMethods(methods -> methods.addAll(List.of(CLIENT_SECRET_BASIC, CLIENT_SECRET_POST)))
            .authorizationGrantTypes(
                types -> types.addAll(List.of(AUTHORIZATION_CODE, CLIENT_CREDENTIALS, REFRESH_TOKEN)))
            .redirectUris(
                redirectUris ->
                    redirectUris.addAll(
                        authorizationClient.getRedirectUris().stream()
                            .map(AuthorizationClientRedirect::getRedirectUri)
                            .toList()))
            .scopes(
                scopes -> {
                    scopes.add(OidcScopes.OPENID);
                    scopes.addAll(
                        authorizationClient.getScopes().stream()
                            .map(item -> item.getScope().getScope())
                            .toList());
                })
            .clientSettings(
                ClientSettings.builder()
                    .requireAuthorizationConsent(authorizationClient.getRequiredAuthorizationConsent())
                    .build())
            .tokenSettings(
                TokenSettings.builder()
                    .accessTokenTimeToLive(Duration.ofSeconds(authorizationClient.getAccessTokenTimeToLive()))
                    .build())
            .build();
    }
}
