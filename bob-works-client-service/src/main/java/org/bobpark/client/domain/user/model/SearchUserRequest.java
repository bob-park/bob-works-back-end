package org.bobpark.client.domain.user.model;

import java.util.List;

import lombok.Builder;

@Builder
public record SearchUserRequest(List<Long> ids) {
}
