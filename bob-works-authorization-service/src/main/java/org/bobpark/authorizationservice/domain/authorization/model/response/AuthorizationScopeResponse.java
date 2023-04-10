package org.bobpark.authorizationservice.domain.authorization.model.response;

import lombok.Builder;

@Builder
public record AuthorizationScopeResponse(
    Long id,
    String scope,
    String description) {
}
