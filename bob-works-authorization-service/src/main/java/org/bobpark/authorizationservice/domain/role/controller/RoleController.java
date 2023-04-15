package org.bobpark.authorizationservice.domain.role.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.authorizationservice.domain.role.model.RoleResponse;
import org.bobpark.authorizationservice.domain.role.service.RoleService;

@RequiredArgsConstructor
@RestController
@RequestMapping("role")
public class RoleController {

    private final RoleService roleService;

    @GetMapping(path = "")
    public List<RoleResponse> getRoles() {
        return roleService.getRoles();
    }
}
