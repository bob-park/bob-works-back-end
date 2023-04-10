package org.bobpark.authorizationservice.domain.role.service.impl;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

import org.bobpark.authorizationservice.domain.role.entity.Role;
import org.bobpark.authorizationservice.domain.role.repository.RoleRepository;
import org.bobpark.authorizationservice.domain.role.service.RoleHierarchyService;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RoleHierarchyServiceImpl implements RoleHierarchyService {

    private final RoleRepository roleRepository;

    @Override
    public Map<String, List<String>> getRoleHierarchyToMap() {

        Map<String, List<String>> result = Maps.newHashMap();

        List<Role> roles = roleRepository.findAll();

        for (Role role : roles) {
            if (!role.getChildren().isEmpty()) {
                result.put(role.getRoleName(), role.getChildren().stream().map(Role::getRoleName).toList());
            }
        }

        return result;
    }
}
