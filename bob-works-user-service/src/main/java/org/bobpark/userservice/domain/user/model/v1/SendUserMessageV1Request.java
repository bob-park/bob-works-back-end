package org.bobpark.userservice.domain.user.model.v1;

import lombok.Builder;

import org.bobpark.userservice.domain.user.model.SendUserMessageRequest;

@Builder
public record SendUserMessageV1Request(String message)
    implements SendUserMessageRequest {
}
