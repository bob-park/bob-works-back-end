package org.bobpark.maintenanceservice.domain.maintenance.repository.query;

import java.util.Optional;

import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatRoom;

public interface CustomerChatRoomQueryRepository {

    Optional<CustomerChatRoom> getLatestChatRoom(long customerId);
}
