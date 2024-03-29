package org.bobpark.maintenanceservice.domain.role.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.bobpark.maintenanceservice.domain.role.model.RoleResponse;

@FeignClient(name = "authorization-service")
public interface RoleClient {

    @GetMapping(path = "role")
    List<RoleResponse> getRoles();
}
