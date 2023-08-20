package org.bobpark.maintenanceservice.domain.maintenance.entity;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.apache.commons.lang.StringUtils;

import org.bobpark.maintenanceservice.common.entity.BaseTimeEntity;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "maintenance_customer_chats")
public class CustomerChat extends BaseTimeEntity<CustomerChatId> {

    @EmbeddedId
    private CustomerChatId id;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private CustomerChatRoom room;

    private Long writerId;
    private String contents;
    private Boolean isRead;

    @Builder
    private CustomerChat(CustomerChatId id, Long writerId, String contents, Boolean isRead) {

        checkArgument(isNotEmpty(id), "id must be provided.");
        checkArgument(isNotEmpty(writerId), "writerId must be provided.");
        checkArgument(StringUtils.isNotBlank(contents), "contents must be provided.");

        this.id = id;
        this.writerId = writerId;
        this.contents = contents;
        this.isRead = defaultIfNull(isRead, false);
    }

    public void setRoom(CustomerChatRoom room) {
        this.room = room;
    }
}
