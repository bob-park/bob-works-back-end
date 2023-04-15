package org.bobpark.authorizationservice.domain.role.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;

import org.bobpark.authorizationservice.domain.role.entity.Role;

@Builder
public record RoleResponse(Long id,
                           String roleName,
                           String description,
                           RoleResponse parent,
                           List<RoleResponse> children) {

    public static RoleResponse toResponse(Role role) {
        return RoleResponse.builder()
            .id(role.getId())
            // .parent(role.getParent() != null ? toResponse(role.getParent()) : null)
            .roleName(role.getRoleName())
            .description(role.getDescription())
            .children(new ArrayList<>())
            .build();
    }
}
