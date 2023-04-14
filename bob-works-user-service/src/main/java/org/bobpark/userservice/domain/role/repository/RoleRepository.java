package org.bobpark.userservice.domain.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.userservice.domain.role.entity.Role;
import org.bobpark.userservice.domain.role.repository.query.RoleQueryRepository;

public interface RoleRepository extends JpaRepository<Role, Long>, RoleQueryRepository {
}
