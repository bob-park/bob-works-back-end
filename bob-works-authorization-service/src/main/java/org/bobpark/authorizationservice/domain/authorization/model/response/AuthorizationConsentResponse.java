package org.bobpark.authorizationservice.domain.authorization.model.response;

import java.util.List;

import lombok.Builder;

@Builder
public record AuthorizationConsentResponse(
    Long id,
    AuthorizationClientResponse client,
    String principalName,
    List<String> authorities
) {
}
