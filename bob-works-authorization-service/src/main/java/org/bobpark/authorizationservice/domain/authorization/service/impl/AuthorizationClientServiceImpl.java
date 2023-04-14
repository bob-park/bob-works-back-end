package org.bobpark.authorizationservice.domain.authorization.service.impl;

import static com.google.common.base.Preconditions.*;
import static org.springframework.util.StringUtils.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationClient;
import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationClientRedirect;
import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationClientScope;
import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationScope;
import org.bobpark.authorizationservice.domain.authorization.model.request.CreateAuthorizationClientRequest;
import org.bobpark.authorizationservice.domain.authorization.model.response.AuthorizationClientResponse;
import org.bobpark.authorizationservice.domain.authorization.model.response.AuthorizationScopeResponse;
import org.bobpark.authorizationservice.domain.authorization.repository.AuthorizationClientRepository;
import org.bobpark.authorizationservice.domain.authorization.repository.AuthorizationScopeRepository;
import org.bobpark.authorizationservice.domain.authorization.service.AuthorizationClientService;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthorizationClientServiceImpl implements AuthorizationClientService {

    private static final Pattern DEFAULT_CLIENT_SECRET_PATTERN = Pattern.compile("\\{(.*)\\}(.*)");

    private final AuthorizationClientRepository clientRepository;
    private final AuthorizationScopeRepository scopeRepository;

    @Transactional
    @Override
    public AuthorizationClientResponse createClient(CreateAuthorizationClientRequest createRequest) {

        checkArgument(hasText(createRequest.clientName()), "clientName must be provided.");
        checkArgument(!createRequest.redirectUris().isEmpty(), "redirectUris must be provided.");
        checkArgument(!createRequest.scopes().isEmpty(), "scopes must be provided.");

        AuthorizationClient createClient =
            AuthorizationClient.builder()
                .clientName(createRequest.clientName())
                .build();

        getScopes().stream()
            .filter(item -> createRequest.scopes().contains(item.getScope()))
            .forEach(createClient::addScope);

        createRequest.redirectUris().forEach(createClient::addRedirectUri);

        clientRepository.save(createClient);

        log.debug("added authorization client. (id={})", createClient.getId());

        Matcher matcher = DEFAULT_CLIENT_SECRET_PATTERN.matcher(createClient.getClientSecret());
        matcher.find();

        log.debug("clientSecret={}", matcher.group(2));

        return AuthorizationClientResponse.builder()
            .id(createClient.getId())
            .clientId(createClient.getClientId())
            .clientSecret(matcher.group(2))
            .clientIssueAt(createClient.getClientIssueAt())
            .clientSecretExpiredAt(createClient.getClientSecretExpiresAt())
            .requiredAuthorizationConsent(createClient.getRequiredAuthorizationConsent())
            .accessTokenTimeToLive(createClient.getAccessTokenTimeToLive())
            .scopes(
                createClient.getScopes().stream()
                    .map(AuthorizationClientScope::getScope)
                    .map(scope ->
                        AuthorizationScopeResponse.builder()
                            .id(scope.getId())
                            .scope(scope.getScope())
                            .build())
                    .toList())
            .redirectUris(
                createClient.getRedirectUris().stream()
                    .map(AuthorizationClientRedirect::getRedirectUri)
                    .toList())
            .createdDate(createClient.getCreatedDate())
            .lastModifiedDate(createClient.getLastModifiedDate())
            .build();
    }

    private List<AuthorizationScope> getScopes() {
        return scopeRepository.findAll();
    }
}
