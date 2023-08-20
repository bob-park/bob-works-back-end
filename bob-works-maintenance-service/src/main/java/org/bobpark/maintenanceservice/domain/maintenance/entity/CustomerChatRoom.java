package org.bobpark.maintenanceservice.domain.maintenance.entity;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
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
@Table(name = "maintenance_customer_chat_rooms")
public class CustomerChatRoom extends BaseTimeEntity<CustomerChatRoomId> {

    @EmbeddedId
    private CustomerChatRoomId id;

    private Long customerId;
    private Long managerId;
    private String title;
    private String description;

    @Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<CustomerChat> chatList = new ArrayList<>();

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

    public void addChat(CustomerChat chat) {
        chat.setRoom(this);
        getChatList().add(chat);
    }
}
