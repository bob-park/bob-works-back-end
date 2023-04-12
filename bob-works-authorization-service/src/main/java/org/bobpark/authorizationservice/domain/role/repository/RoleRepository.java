package org.bobpark.authorizationservice.domain.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.authorizationservice.domain.role.entity.Role;
import org.bobpark.authorizationservice.domain.role.repository.query.RoleQueryRepository;

public interface RoleRepository extends JpaRepository<Role, Long>, RoleQueryRepository {
}
