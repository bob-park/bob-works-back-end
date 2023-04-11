package org.bobpark.authorizationservice.domain.authorization.model;

import lombok.Builder;

@Builder
public record SearchAuthorizationSessionCondition(
    String authorizationCodeValue,
    String accessToken,
    String refreshToken,
    String state
) {
}
