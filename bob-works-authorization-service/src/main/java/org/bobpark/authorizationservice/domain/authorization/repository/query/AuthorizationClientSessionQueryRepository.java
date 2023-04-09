package org.bobpark.authorizationservice.domain.authorization.repository.query;

import java.util.Optional;

import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationClientSession;
import org.bobpark.authorizationservice.domain.authorization.model.SearchAuthorizationSessionCondition;

public interface AuthorizationClientSessionQueryRepository {

    Optional<AuthorizationClientSession> findBy(SearchAuthorizationSessionCondition condition);
}
