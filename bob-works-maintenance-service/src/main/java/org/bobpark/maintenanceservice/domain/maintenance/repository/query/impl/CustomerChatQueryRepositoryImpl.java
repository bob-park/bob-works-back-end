package org.bobpark.maintenanceservice.domain.maintenance.repository.query.impl;

import static org.bobpark.maintenanceservice.domain.maintenance.entity.QCustomerChat.*;
import static org.bobpark.maintenanceservice.domain.maintenance.entity.QCustomerChatRoom.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import org.apache.commons.lang.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChat;
import org.bobpark.maintenanceservice.domain.maintenance.entity.CustomerChatRoomId;
import org.bobpark.maintenanceservice.domain.maintenance.entity.QCustomerChat;
import org.bobpark.maintenanceservice.domain.maintenance.entity.QCustomerChatRoom;
import org.bobpark.maintenanceservice.domain.maintenance.model.SearchCustomerChatCondition;
import org.bobpark.maintenanceservice.domain.maintenance.repository.query.CustomerChatQueryRepository;

@RequiredArgsConstructor
public class CustomerChatQueryRepositoryImpl implements CustomerChatQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<CustomerChat> search(SearchCustomerChatCondition searchCondition, Pageable pageable) {

        List<CustomerChat> content =
            query.selectFrom(customerChat)
                .join(customerChat.room, customerChatRoom).fetchJoin()
                .where(mappingCondition(searchCondition))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(customerChat.createdDate.desc())
                .fetch();

        JPAQuery<Long> countQuery = query.select(customerChat.id.count())
            .from(customerChat)
            .join(customerChat.room, customerChatRoom)
            .where(mappingCondition(searchCondition));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private Predicate mappingCondition(SearchCustomerChatCondition searchCondition) {
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.isNotBlank(searchCondition.roomId())) {
            builder.and(eqRoomId(new CustomerChatRoomId(searchCondition.roomId())));
        }

        return builder;
    }

    private BooleanExpression eqRoomId(CustomerChatRoomId roomId) {
        return roomId != null ? customerChatRoom.id.eq(roomId) : null;
    }
}
