package org.bobpark.userservice.domain.user.repository.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import org.bobpark.userservice.domain.user.entity.notification.UserNotificationServer;

public interface UserNotificationServerRepository extends JpaRepository<UserNotificationServer, Long> {
}
