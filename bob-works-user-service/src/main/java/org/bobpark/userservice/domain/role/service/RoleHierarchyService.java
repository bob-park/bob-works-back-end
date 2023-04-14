package org.bobpark.userservice.domain.role.service;

import java.util.List;
import java.util.Map;

public interface RoleHierarchyService {

    Map<String, List<String>> getRoleHierarchyToMap();
}
