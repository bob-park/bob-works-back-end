package org.bobpark.authorizationservice.domain.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationConsent;
import org.bobpark.authorizationservice.domain.authorization.repository.query.AuthorizationConsentQueryRepository;

public interface AuthorizationConsentRepository extends JpaRepository<AuthorizationConsent, Long>,
    AuthorizationConsentQueryRepository {
}
