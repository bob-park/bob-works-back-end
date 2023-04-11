package org.bobpark.authorizationservice.domain.authorization.repository.query;

import java.util.Optional;

import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationClient;

public interface AuthorizationClientQueryRepository {

    Optional<AuthorizationClient> findByClientId(String clientId);
}
