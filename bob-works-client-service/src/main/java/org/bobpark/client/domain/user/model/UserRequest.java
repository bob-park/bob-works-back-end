package org.bobpark.client.domain.user.model;

import lombok.Builder;

@Builder
public record UserRequest(String id, String userId) {
}
