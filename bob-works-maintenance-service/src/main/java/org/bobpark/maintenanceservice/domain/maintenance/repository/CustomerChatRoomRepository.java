package org.bobpark.maintenanceservice.domain.maintenance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatRoom;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatRoomId;
import org.bobpark.maintenanceservice.domain.maintenance.repository.query.CustomerChatRoomQueryRepository;

public interface CustomerChatRoomRepository extends JpaRepository<CustomerChatRoom, CustomerChatRoomId>,
    CustomerChatRoomQueryRepository {
}
