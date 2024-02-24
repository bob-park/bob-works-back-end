package org.bobpark.userservice.domain.user.entity.notification;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

import org.bobpark.userservice.common.entity.BaseEntity;
import org.bobpark.userservice.domain.user.entity.User;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users_notification_servers")
public class UserNotificationServer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String hookUrl;

    @Builder
    private UserNotificationServer(Long id, NotificationType type, String hookUrl) {

        checkArgument(isNotEmpty(type), "type must be provided.");

        this.id = id;
        this.type = type;
        this.hookUrl = hookUrl;
    }

    /*
    편의 메서드
     */
    public void updateUser(User user) {
        this.user = user;
    }

}
