package org.bobpark.authorizationservice.domain.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationClientSession;
import org.bobpark.authorizationservice.domain.authorization.repository.query.AuthorizationClientSessionQueryRepository;

public interface AuthorizationClientSessionRepository extends JpaRepository<AuthorizationClientSession, Long>,
    AuthorizationClientSessionQueryRepository {
}
