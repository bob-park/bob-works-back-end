package org.bobpark.userservice.domain.role.repository.query;

import java.util.List;

import org.bobpark.userservice.domain.role.entity.Role;

public interface RoleQueryRepository {

    List<Role> findAll();

}
