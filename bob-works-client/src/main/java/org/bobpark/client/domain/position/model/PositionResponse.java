package org.bobpark.client.domain.position.model;

import lombok.Builder;

@Builder
public record PositionResponse(Long id,
                               String name) {

}
