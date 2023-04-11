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
public class OidcToken {

    @Embedded
    @AttributeOverride(name ="value", column = @Column(name = "oidc_id_token_value", columnDefinition = "text"))
    @AttributeOverride(name ="issuedAt", column = @Column(name = "oidc_id_token_issued_at"))
    @AttributeOverride(name ="expiresAt", column = @Column(name = "oidc_id_token_expires_at"))
    @AttributeOverride(name ="metadata", column = @Column(name = "oidc_id_token_metadata", columnDefinition = "bytea"))
    private AuthorizationToken token;

    @Builder
    private OidcToken(AuthorizationToken token) {
        this.token = token;
    }
}
