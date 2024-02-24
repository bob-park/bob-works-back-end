package org.bobpark.userservice.domain.user.model.v1;

import lombok.Builder;

import org.bobpark.userservice.domain.user.model.UserNotificationResponse;

@Builder
public record UserNotificationV1Response(String message)
    implements UserNotificationResponse {
}
