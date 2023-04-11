package org.bobpark.authorizationservice.domain.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import org.bobpark.authorizationservice.domain.authorization.entity.AuthorizationScope;

public interface AuthorizationScopeRepository extends JpaRepository<AuthorizationScope, Long> {
}
