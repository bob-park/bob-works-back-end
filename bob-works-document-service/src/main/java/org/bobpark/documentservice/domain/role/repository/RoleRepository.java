package org.bobpark.documentservice.domain.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.documentservice.domain.role.entity.Role;
import org.bobpark.documentservice.domain.role.repository.query.RoleQueryRepository;

public interface RoleRepository extends JpaRepository<Role, Long>, RoleQueryRepository {
}
