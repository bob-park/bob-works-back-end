package org.bobpark.authorizationservice.domain.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.authorizationservice.domain.role.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
