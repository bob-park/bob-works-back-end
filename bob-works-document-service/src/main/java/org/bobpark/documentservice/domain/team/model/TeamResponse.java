package org.bobpark.documentservice.domain.team.model;

import lombok.Builder;

@Builder
public record TeamResponse(Long id,
                           String name,
                           String description) {

}
