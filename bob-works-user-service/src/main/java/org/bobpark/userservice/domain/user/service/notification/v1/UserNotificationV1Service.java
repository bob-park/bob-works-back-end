package org.bobpark.userservice.domain.user.service.notification.v1;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ObjectUtils.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;
import com.netflix.discovery.provider.Serializer;

import org.bobpark.core.exception.NotFoundException;
import org.bobpark.core.model.common.Id;
import org.bobpark.userservice.domain.user.entity.User;
import org.bobpark.userservice.domain.user.entity.notification.UserNotificationServer;
import org.bobpark.userservice.domain.user.model.SendUserMessageRequest;
import org.bobpark.userservice.domain.user.model.UserNotificationResponse;
import org.bobpark.userservice.domain.user.model.v1.SendUserMessageV1Request;
import org.bobpark.userservice.domain.user.model.v1.UserNotificationV1Response;
import org.bobpark.userservice.domain.user.producer.notification.DelegatingNotificationProducer;
import org.bobpark.userservice.domain.user.producer.notification.SendCommand;
import org.bobpark.userservice.domain.user.repository.UserRepository;
import org.bobpark.userservice.domain.user.service.notification.UserNotificationService;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserNotificationV1Service implements UserNotificationService {

    private final UserRepository userRepository;

    private final DelegatingNotificationProducer notificationProducer;

    @Override
    public UserNotificationResponse sendMessage(Id<User, Long> userId, SendUserMessageRequest sendRequest) {

        SendUserMessageV1Request sendV1Request = (SendUserMessageV1Request)sendRequest;

        checkArgument(StringUtils.isNotBlank(sendV1Request.message()), "message must be provided.");

        User user =
            userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(userId));

        for (UserNotificationServer notificationServer : user.getNotificationServers()) {
            notificationProducer.send(
                notificationServer.getType(),
                SendCommand.builder()
                    .hookUrl(notificationServer.getHookUrl())
                    .message(sendV1Request.message())
                    .build());
        }

        return UserNotificationV1Response.builder()
            .message(sendV1Request.message())
            .build();
    }
}
