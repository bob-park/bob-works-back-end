package org.bobpark.authorizationservice.domain.authorization.repository.query;

import java.util.Optional;

import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationConsent;

public interface AuthorizationConsentQueryRepository {

    Optional<AuthorizationConsent> findBy(long clientId, String principalName);
}
