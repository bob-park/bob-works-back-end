package org.bobpark.maintenanceservice.domain.maintenance.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChat;
import org.bobpark.maintenanceservice.domain.maintenance.model.SearchCustomerChatCondition;

public interface CustomerChatQueryRepository {

    Page<CustomerChat> search(SearchCustomerChatCondition searchCondition, Pageable pageable);
}
