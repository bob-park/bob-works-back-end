package org.bobpark.authorizationservice.domain.role.repository.query;

import java.util.List;

import org.bobpark.authorizationservice.domain.role.entity.Role;

public interface RoleQueryRepository {

    List<Role> findAll();

}
