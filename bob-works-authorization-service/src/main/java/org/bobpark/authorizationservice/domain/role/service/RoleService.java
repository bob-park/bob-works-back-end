package org.bobpark.authorizationservice.domain.role.service;

import java.util.List;

import org.bobpark.authorizationservice.domain.role.model.RoleResponse;

public interface RoleService {

    List<RoleResponse> getRoles();
}
