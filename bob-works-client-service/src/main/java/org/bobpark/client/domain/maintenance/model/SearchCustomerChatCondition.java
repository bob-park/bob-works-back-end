package org.bobpark.client.domain.maintenance.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record SearchCustomerChatCondition(String roomId) {
}
