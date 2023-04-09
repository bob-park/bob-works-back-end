package org.bobpark.authorizationservice.domain.authorization.entity.token;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class RefreshToken {

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "refresh_token_value", columnDefinition = "text"))
    @AttributeOverride(name = "issuedAt", column = @Column(name = "refresh_token_issued_at"))
    @AttributeOverride(name = "expiresAt", column = @Column(name = "refresh_token_expires_at"))
    @AttributeOverride(name = "metadata", column = @Column(name = "refresh_token_metadata", columnDefinition = "bytea"))
    private AuthorizationToken token;

    @Builder
    private RefreshToken(AuthorizationToken token) {
        this.token = token;
    }
}
