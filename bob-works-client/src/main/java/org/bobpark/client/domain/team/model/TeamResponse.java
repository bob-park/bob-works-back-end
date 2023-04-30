package org.bobpark.client.domain.team.model;

import lombok.Builder;

@Builder
public record TeamResponse(Long id,
                           String name,
                           String description) {

}
