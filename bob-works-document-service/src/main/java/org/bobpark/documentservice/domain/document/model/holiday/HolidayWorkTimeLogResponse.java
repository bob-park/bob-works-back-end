package org.bobpark.documentservice.domain.document.model.holiday;

import lombok.Builder;

@Builder
public record HolidayWorkTimeLogResponse(Long id,
                                         String calculationLog) {
}

