package org.bobpark.userservice.domain.user.service.notification;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.SendUserMessageRequest;
import org.bobpark.userservice.domain.user.model.UserNotificationResponse;

public interface UserNotificationService {

    UserNotificationResponse sendMessage(Id<User, Long> userId, SendUserMessageRequest sendRequest);

}
