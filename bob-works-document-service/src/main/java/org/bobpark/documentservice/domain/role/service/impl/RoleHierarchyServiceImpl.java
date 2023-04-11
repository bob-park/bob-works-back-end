package org.bobpark.documentservice.domain.role.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

import org.bobpark.documentservice.domain.role.entity.Role;
import org.bobpark.documentservice.domain.role.repository.RoleRepository;
import org.bobpark.documentservice.domain.role.service.RoleHierarchyService;

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

        List<RoleHierarchy> roleHierarchies = new ArrayList<>();

        for (Role role : roles) {

            if (role.getParent() != null) {
                roleHierarchies.stream().filter(item -> item.getRole().equals(role.getParent().getRoleName()))
                    .findAny()
                    .ifPresent(roleHierarchy ->
                        roleHierarchy.getChildern().add(role.getRoleName()));
            }

            roleHierarchies.add(new RoleHierarchy(role.getRoleName()));
        }

        roleHierarchies
            .stream()
            .filter(roleHierarchy -> !roleHierarchy.getChildern().isEmpty())
            .forEach(roleHierarchy -> result.put(roleHierarchy.getRole(), roleHierarchy.getChildern()));

        return result;
    }

    @ToString
    @Getter
    private class RoleHierarchy {
        private final String role;
        private final List<String> childern = new ArrayList<>();

        public RoleHierarchy(String role) {
            this.role = role;
        }
    }
}
