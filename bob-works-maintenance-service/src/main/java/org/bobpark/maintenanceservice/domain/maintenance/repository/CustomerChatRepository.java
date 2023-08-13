package org.bobpark.maintenanceservice.domain.maintenance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChat;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatId;

public interface CustomerChatRepository extends JpaRepository<CustomerChat, CustomerChatId> {
}
