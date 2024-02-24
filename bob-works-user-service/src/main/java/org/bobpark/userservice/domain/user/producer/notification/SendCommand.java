package org.bobpark.userservice.domain.user.producer.notification;

public record SendCommand(String hookUrl, String message) {
}
