package org.bobpark.authorizationservice.domain.posotion.model;

import lombok.Builder;

@Builder
public record PositionResponse(Long id,
                               String name) {

}
