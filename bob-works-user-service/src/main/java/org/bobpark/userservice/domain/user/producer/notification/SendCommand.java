package org.bobpark.userservice.domain.user.producer.notification;

import lombok.Builder;

@Builder
public record SendCommand(String hookUrl, String message) {
}
