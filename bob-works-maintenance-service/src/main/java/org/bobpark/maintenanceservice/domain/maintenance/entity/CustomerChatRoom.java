package org.bobpark.maintenanceservice.domain.maintenance.entity;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.apache.commons.lang.StringUtils;

import org.bobpark.maintenanceservice.common.entity.BaseEntity;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "maintenance_customer_chat_rooms")
public class CustomerChatRoom extends BaseEntity<CustomerChatRoomId> {

    @EmbeddedId
    private CustomerChatRoomId id;

    private Long customerId;
    private Long managerId;
    private String title;
    private String description;

    @Builder
    private CustomerChatRoom(CustomerChatRoomId id, Long customerId, Long managerId, String title, String description) {

        checkArgument(isNotEmpty(id), "id must be provided.");
        checkArgument(isNotEmpty(customerId), "customerId must be provided.");
        checkArgument(StringUtils.isNotBlank(title), "title must be provided.");

        this.id = id;
        this.customerId = customerId;
        this.managerId = managerId;
        this.title = title;
        this.description = description;
    }
}
