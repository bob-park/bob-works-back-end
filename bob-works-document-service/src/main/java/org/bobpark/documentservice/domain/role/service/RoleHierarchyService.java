package org.bobpark.documentservice.domain.role.service;

import java.util.List;
import java.util.Map;

public interface RoleHierarchyService {

    Map<String, List<String>> getRoleHierarchyToMap();
}
