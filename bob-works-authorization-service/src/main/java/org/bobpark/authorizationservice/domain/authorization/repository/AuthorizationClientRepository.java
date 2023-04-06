package org.bobpark.authorizationservice.domain.authorization.repository;

import java.util.Optional;

import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationClientRepository extends JpaRepository<AuthorizationClient, Long> {
    AuthorizationClient getByClientId(String clientId);
}
