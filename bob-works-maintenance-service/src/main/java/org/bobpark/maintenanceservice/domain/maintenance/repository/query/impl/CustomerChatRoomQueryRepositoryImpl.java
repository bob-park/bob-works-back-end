package org.bobpark.maintenanceservice.domain.maintenance.repository.query.impl;

import static org.bobpark.maintenanceservice.domain.maintenance.entity.QCustomerChatRoom.*;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatRoom;
import org.bobpark.maintenanceservice.domain.maintenance.entity.QCustomerChatRoom;
import org.bobpark.maintenanceservice.domain.maintenance.repository.query.CustomerChatRoomQueryRepository;

@RequiredArgsConstructor
public class CustomerChatRoomQueryRepositoryImpl implements CustomerChatRoomQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<CustomerChatRoom> getLatestChatRoom(long customerId) {
        return Optional.ofNullable(
            query.selectFrom(customerChatRoom)
                .where(customerChatRoom.customerId.eq(customerId))
                .offset(0)
                .limit(1)
                .orderBy(customerChatRoom.createdDate.desc())
                .fetchOne());
    }
}
