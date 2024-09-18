package org.bobpark.maintenanceservice.domain.maintenance.repository.query.impl;

import static org.bobpark.maintenanceservice.domain.maintenance.entity.QCustomerChatRoom.*;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

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

    @Override
    public Page<CustomerChatRoom> getChatRooms(Pageable pageable) {

        List<CustomerChatRoom> content =
            query.selectFrom(customerChatRoom)
                .where()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(customerChatRoom.createdDate.desc())
                .fetch();

        JPAQuery<Long> countQuery =
            query.select(customerChatRoom.id.count())
                .from(customerChatRoom)
                .where();

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
