package org.bobpark.authorizationservice.domain.authorization.model.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public record AuthorizationClientResponse(
    Long id,
    String clientId,
    String clientSecret,
    String clientName,
    LocalDateTime clientIssueAt,
    LocalDateTime clientSecretExpiredAt,
    Boolean requiredAuthorizationConsent,
    Long accessTokenTimeToLive,
    List<String> redirectUris,
    List<AuthorizationScopeResponse> scopes,
    LocalDateTime createdDate,
    LocalDateTime lastModifiedDate) {
}
