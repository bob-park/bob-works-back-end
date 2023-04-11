package org.bobpark.authorizationservice.domain.authorization.entity.token;

import java.util.List;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.bobpark.authorizationservice.domain.authorization.converter.SpaceDelimitedConverter;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class AccessToken{

    @Column(name = "access_token_type")
    private String tokenType;

    @Column(name = "access_token_scopes")
    @Convert(converter = SpaceDelimitedConverter.class)
    private List<String> scopes;


    @Embedded
    @AttributeOverride(name ="value", column = @Column(name = "access_token_value", columnDefinition = "text"))
    @AttributeOverride(name ="issuedAt", column = @Column(name = "access_token_issued_at"))
    @AttributeOverride(name ="expiresAt", column = @Column(name = "access_token_expires_at"))
    @AttributeOverride(name ="metadata", column = @Column(name = "access_token_metadata", columnDefinition = "bytea"))
    private AuthorizationToken token;

    @Builder
    private AccessToken(String tokenType, List<String> scopes, AuthorizationToken token) {
        this.tokenType = tokenType;
        this.scopes = scopes;
        this.token = token;
    }
}
