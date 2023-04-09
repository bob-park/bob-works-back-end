package org.bobpark.authorizationservice.authorization.client;

import static org.apache.commons.lang3.math.NumberUtils.*;
import static org.springframework.util.StringUtils.*;

import java.time.Instant;
import java.time.ZoneId;
import java.util.HashSet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization.Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationClient;
import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationClientSession;
import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationClientSession.AuthorizationClientSessionBuilder;
import org.bobpark.authorizationservice.domain.authorization.entity.token.AccessToken;
import org.bobpark.authorizationservice.domain.authorization.entity.token.AuthorizationToken;
import org.bobpark.authorizationservice.domain.authorization.entity.token.OidcToken;
import org.bobpark.authorizationservice.domain.authorization.entity.token.RefreshToken;
import org.bobpark.authorizationservice.domain.authorization.model.SearchAuthorizationSessionCondition;
import org.bobpark.authorizationservice.domain.authorization.model.SearchAuthorizationSessionCondition.SearchAuthorizationSessionConditionBuilder;
import org.bobpark.authorizationservice.domain.authorization.repository.AuthorizationClientRepository;
import org.bobpark.authorizationservice.domain.authorization.repository.AuthorizationClientSessionRepository;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final RegisteredClientRepository registeredClientRepository;
    private final AuthorizationClientRepository clientRepository;
    private final AuthorizationClientSessionRepository clientSessionRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    @Transactional
    @Override
    public void save(OAuth2Authorization authorization) {

        AuthorizationClientSession clientSession = toClientSession(authorization);

        clientSessionRepository.save(clientSession);

        log.debug("saved client session. (id={})", clientSession);
    }

    @Transactional
    @Override
    public void remove(OAuth2Authorization authorization) {

        AuthorizationClientSession clientSession = toClientSession(authorization);

        clientSessionRepository.delete(clientSession);
    }

    @Override
    public OAuth2Authorization findById(String id) {

        AuthorizationClientSession clientSession =
            clientSessionRepository.findById(toLong(id))
                .orElseThrow();

        return toAuthorization(clientSession);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");

        SearchAuthorizationSessionConditionBuilder builder = SearchAuthorizationSessionCondition.builder();
        if (tokenType == null) {
            // TODO token type 이 null 인 경우도 추가해보자
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            // TODO state 추가하자
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            builder.authorizationCodeValue(token);
        } else if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
            builder.accessToken(token);
        } else if (OAuth2TokenType.REFRESH_TOKEN.equals(tokenType)) {
            builder.refreshToken(token);
        }

        AuthorizationClientSession clientSession = clientSessionRepository.findBy(builder.build()).orElse(null);

        return toAuthorization(clientSession);
    }

    private OAuth2Authorization toAuthorization(AuthorizationClientSession clientSession) {

        if (clientSession == null) {
            return null;
        }

        RegisteredClient registeredClient = registeredClientRepository.findById(
            String.valueOf(clientSession.getClient().getId()));

        if (registeredClient == null) {
            throw new DataRetrievalFailureException(
                "The RegisteredClient with id '" + clientSession.getClient().getId()
                    + "' was not found in the RegisteredClientRepository.");
        }

        String grantType = clientSession.getAuthorizationGrantType();

        OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(registeredClient);

        builder.id(String.valueOf(clientSession.getId()))
            .principalName(clientSession.getPrincipalName())
            .authorizationGrantType(new AuthorizationGrantType(grantType))
            .authorizedScopes(new HashSet<>(clientSession.getAuthorizedScopes()))
            .attributes(attributes -> attributes.putAll(clientSession.getAttributes()));

        if (hasText(clientSession.getAuthorizationCodeValue())) {
            OAuth2AuthorizationCode authorizationCode =
                new OAuth2AuthorizationCode(
                    clientSession.getAuthorizationCodeValue(),
                    clientSession.getAuthorizationCodeIssued().atZone(ZoneId.systemDefault()).toInstant(),
                    clientSession.getAuthorizationCodeExpire().atZone(ZoneId.systemDefault()).toInstant());

            builder.token(authorizationCode, metadata -> metadata.putAll(clientSession.getAuthorizationCodeMetadata()));
        }

        AccessToken accessToken = clientSession.getAccessToken();
        OidcToken oidcToken = clientSession.getOidcToken();
        RefreshToken refreshToken = clientSession.getRefreshToken();

        if (accessToken != null && accessToken.getToken() != null && !accessToken.getToken().isEmpty()) {

            AuthorizationToken token = accessToken.getToken();

            OAuth2AccessToken oAuth2AccessToken =
                new OAuth2AccessToken(TokenType.BEARER, token.getValue(), token.getIssuedAtInstant(),
                    token.getExpiresAtInstant());

            builder.token(oAuth2AccessToken, metadata -> metadata.putAll(token.getMetadata()));
        }

        if (oidcToken != null && oidcToken.getToken() != null && !oidcToken.getToken().isEmpty()) {
            AuthorizationToken token = oidcToken.getToken();

            OidcIdToken oidcIdToken =
                new OidcIdToken(token.getValue(), token.getIssuedAtInstant(),
                    token.getExpiresAtInstant(), token.getMetadata());

            builder.token(oidcIdToken, metadata -> metadata.putAll(token.getMetadata()));
        }

        if (refreshToken != null && refreshToken.getToken() != null && !refreshToken.getToken().isEmpty()) {
            AuthorizationToken token = refreshToken.getToken();

            OAuth2RefreshToken oAuth2RefreshToken =
                new OAuth2RefreshToken(token.getValue(), token.getIssuedAtInstant(),
                    token.getExpiresAtInstant());

            builder.token(oAuth2RefreshToken, metadata -> metadata.putAll(token.getMetadata()));
        }

        return builder.build();
    }

    private AuthorizationClientSession toClientSession(OAuth2Authorization authorization) {

        String id = authorization.getId();
        String clientId = authorization.getRegisteredClientId();

        AuthorizationClient authorizationClient =
            clientRepository.findById(toLong(clientId))
                .orElseThrow();

        Token<OAuth2AuthorizationCode> authorizationToken = authorization.getToken(OAuth2AuthorizationCode.class);
        Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        Token<OidcIdToken> oidcToken = authorization.getToken(OidcIdToken.class);
        Token<OAuth2RefreshToken> refreshToken = authorization.getRefreshToken();

        AuthorizationClientSessionBuilder builder =
            AuthorizationClientSession.builder()
                .id(hasText(id) ? toLong(id) : null)
                .client(authorizationClient)
                .principalName(authorization.getPrincipalName())
                .authorizationGrantType(authorization.getAuthorizationGrantType().getValue())
                .authorizedScopes(authorization.getAuthorizedScopes().stream().toList())
                .attributes(authorization.getAttributes());

        if (authorizationToken != null) {

            Instant issuedAt = authorizationToken.getToken().getIssuedAt();
            Instant expiresAt = authorizationToken.getToken().getExpiresAt();

            builder.authorizationCodeValue(authorizationToken.getToken().getTokenValue())
                .authorizationCodeIssued(
                    issuedAt != null ? issuedAt.atZone(ZoneId.systemDefault()).toLocalDateTime() : null)
                .authorizationCodeExpire(
                    expiresAt != null ? expiresAt.atZone(ZoneId.systemDefault()).toLocalDateTime() : null)
                .authorizationCodeMetadata(authorizationToken.getMetadata());
        }

        if (accessToken != null) {
            AccessToken token =
                AccessToken.builder()
                    .token(toAuthorizationToken(accessToken))
                    .scopes(accessToken.getToken().getScopes().stream().toList())
                    .tokenType(accessToken.getToken().getTokenType().getValue())
                    .build();

            builder.accessToken(token);
        }

        builder.oidcToken(OidcToken.builder().token(toAuthorizationToken(oidcToken)).build())
            .refreshToken(RefreshToken.builder().token(toAuthorizationToken(refreshToken)).build());

        return builder.build();
    }

    private <T extends OAuth2Token> AuthorizationToken toAuthorizationToken(Token<T> token) {

        if (token == null) {
            return null;
        }

        Instant issuedAt = token.getToken().getIssuedAt();
        Instant expiresAt = token.getToken().getExpiresAt();

        return AuthorizationToken.builder()
            .value(token.getToken().getTokenValue())
            .issuedAt(issuedAt != null ? issuedAt.atZone(ZoneId.systemDefault()).toLocalDateTime() : null)
            .expiresAt(expiresAt != null ? expiresAt.atZone(ZoneId.systemDefault()).toLocalDateTime() : null)
            .metadata(token.getMetadata())
            .build();
    }
}
