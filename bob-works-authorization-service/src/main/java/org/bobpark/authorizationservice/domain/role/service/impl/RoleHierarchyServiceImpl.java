package org.bobpark.authorizationservice.domain.role.service.impl;

import static org.bobpark.authorizationservice.domain.role.model.RoleResponse.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

import org.bobpark.authorizationservice.domain.role.entity.Role;
import org.bobpark.authorizationservice.domain.role.model.RoleResponse;
import org.bobpark.authorizationservice.domain.role.repository.RoleRepository;
import org.bobpark.authorizationservice.domain.role.service.RoleHierarchyService;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RoleHierarchyServiceImpl implements RoleHierarchyService {

    private final RoleRepository roleRepository;
    @Override
    public List<RoleResponse> getRoles() {

        List<RoleResponse> result = new ArrayList<>();
        List<Role> roles = roleRepository.findAll();

        for (Role role : roles) {

            RoleResponse response = toResponse(role);

            if (role.getParent() != null) {
                result.stream()
                    .filter(item -> item.id().equals(role.getParent().getId()))
                    .findAny()
                    .ifPresent(item -> item.children().add(response));
            }

            result.add(response);
        }

        return result;
    }
}
