package org.bobpark.documentservice.domain.role.repository.query;

import java.util.List;

import org.bobpark.documentservice.domain.role.entity.Role;

public interface RoleQueryRepository {

    List<Role> findAll();

}
