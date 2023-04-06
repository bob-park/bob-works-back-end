package org.bobpark.authorizationservice.domain.authorization.entity;

import static org.apache.commons.lang3.ObjectUtils.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.bobpark.authorizationservice.common.entity.BaseTimeEntity;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "authentication_clients")
public class AuthorizationClient extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientId;
    private String clientSecret;
    private String clientName;
    private LocalDateTime clientIssueAt;
    private LocalDateTime clientSecretExpiresAt;
    private Boolean requiredAuthorizationConsent;
    private Long accessTokenTimeToLive;

    @Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuthorizationClientRedirect> redirectUris = new ArrayList<>();

    @Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuthorizationClientScope> scopes = new ArrayList<>();

    @Builder
    private AuthorizationClient(Long id, String clientId, String clientSecret, String clientName,
        LocalDateTime clientIssueAt, LocalDateTime clientSecretExpiresAt,
        Boolean requiredAuthorizationConsent, Long accessTokenTimeToLive) {
        this.id = id;
        this.clientId = defaultIfNull(clientId, UUID.randomUUID().toString());
        this.clientSecret = defaultIfNull(clientSecret, UUID.randomUUID().toString());
        this.clientName = clientName;
        this.clientIssueAt = defaultIfNull(clientIssueAt, LocalDateTime.now());
        this.clientSecretExpiresAt = clientSecretExpiresAt;
        this.requiredAuthorizationConsent = defaultIfNull(requiredAuthorizationConsent, true);
        this.accessTokenTimeToLive = defaultIfNull(accessTokenTimeToLive, 1_800L);
    }

    public void addRedirectUri(String redirectUri) {
        getRedirectUris().add(
            AuthorizationClientRedirect.builder()
                .client(this)
                .redirectUri(redirectUri)
                .build());
    }

    public void removeRedirectUri(String redirectUri) {
        getRedirectUris().stream()
            .filter(item -> item.getRedirectUri().equals(redirectUri))
            .findAny()
            .ifPresent(item -> getRedirectUris().remove(item));
    }

    public void addScope(AuthorizationScope scope) {
        getScopes().add(
            AuthorizationClientScope.builder()
                .client(this)
                .scope(scope)
                .build());
    }

    public void removeScope(AuthorizationScope scope) {
        getScopes().stream()
            .filter(item -> item.getScope() == scope)
            .findAny()
            .ifPresent(item -> getScopes().remove(item));
    }

    public void setRedirectUris(List<AuthorizationClientRedirect> redirectUris) {
        this.redirectUris = redirectUris;
    }

    public void setScopes(List<AuthorizationClientScope> scopes) {
        this.scopes = scopes;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientSecretExpiresAt(LocalDateTime clientSecretExpiresAt) {
        this.clientSecretExpiresAt = clientSecretExpiresAt;
    }

    public void setRequiredAuthorizationConsent(Boolean requiredAuthorizationConsent) {
        this.requiredAuthorizationConsent = requiredAuthorizationConsent;
    }

    public void setAccessTokenTimeToLive(Long accessTokenTimeToLive) {
        this.accessTokenTimeToLive = accessTokenTimeToLive;
    }
}
