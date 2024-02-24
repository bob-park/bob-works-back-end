package org.bobpark.userservice.domain.user.controller.notification.v1;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.model.UserNotificationResponse;
import org.bobpark.userservice.domain.user.model.v1.SendUserMessageV1Request;
import org.bobpark.userservice.domain.user.service.notification.v1.UserNotificationV1Service;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/user/{userId}/notification")
public class UserNotificationV1Controller {

    private final UserNotificationV1Service notificationService;

    @PostMapping(path = "send")
    public UserNotificationResponse sendMessage(@PathVariable long userId,
        @RequestBody SendUserMessageV1Request sendRequest) {
        return notificationService.sendMessage(Id.of(User.class, userId), sendRequest);
    }

}
