package org.bobpark.documentservice.domain.role.model;

import java.util.List;

public record RoleResponse(Long id,
                           String roleName,
                           String description,
                           RoleResponse parent,
                           List<RoleResponse> children) {
}
