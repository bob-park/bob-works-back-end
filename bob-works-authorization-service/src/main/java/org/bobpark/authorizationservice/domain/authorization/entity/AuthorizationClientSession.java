package org.bobpark.authorizationservice.domain.authorization.entity;

import static org.springframework.util.StringUtils.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.springframework.data.domain.Persistable;

import org.bobpark.authorizationservice.domain.authorization.converter.BlobToMapConverter;
import org.bobpark.authorizationservice.domain.authorization.converter.ScopeListConverter;
import org.bobpark.authorizationservice.domain.authorization.entity.token.AccessToken;
import org.bobpark.authorizationservice.domain.authorization.entity.token.OidcToken;
import org.bobpark.authorizationservice.domain.authorization.entity.token.RefreshToken;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "authorization_clients_sessions")
public class AuthorizationClientSession implements Persistable<String> {

    @Id
    private String id;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private AuthorizationClient client;

    private String principalName;

    private String authorizationGrantType;

    @Convert(converter = ScopeListConverter.class)
    private List<String> authorizedScopes;

    @Column(columnDefinition = "bytea")
    @Convert(converter = BlobToMapConverter.class)
    private Map<String, Object> attributes;

    private String state;

    @Column(columnDefinition = "text")
    private String authorizationCodeValue;

    private LocalDateTime authorizationCodeIssued;
    private LocalDateTime authorizationCodeExpire;

    @Column(columnDefinition = "bytea")
    @Convert(converter = BlobToMapConverter.class)
    private Map<String, Object> authorizationCodeMetadata;


    @Embedded
    private AccessToken accessToken;

    @Embedded
    private OidcToken oidcToken;

    @Embedded
    private RefreshToken refreshToken;

    @Builder
    private AuthorizationClientSession(String id, AuthorizationClient client, String principalName,
        String authorizationGrantType, List<String> authorizedScopes, Map<String, Object> attributes, String state,
        String authorizationCodeValue, LocalDateTime authorizationCodeIssued, LocalDateTime authorizationCodeExpire,
        Map<String, Object> authorizationCodeMetadata, AccessToken accessToken, OidcToken oidcToken,
        RefreshToken refreshToken) {
        this.id = id;
        this.client = client;
        this.principalName = principalName;
        this.authorizationGrantType = authorizationGrantType;
        this.authorizedScopes = authorizedScopes;
        this.attributes = attributes;
        this.state = state;
        this.authorizationCodeValue = authorizationCodeValue;
        this.authorizationCodeIssued = authorizationCodeIssued;
        this.authorizationCodeExpire = authorizationCodeExpire;
        this.authorizationCodeMetadata = authorizationCodeMetadata;
        this.accessToken = accessToken;
        this.oidcToken = oidcToken;
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean isNew() {
        return !hasText(id);
    }
}
