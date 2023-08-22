package org.bobpark.userservice.domain.user.cqrs.event;

import lombok.Builder;

@Builder
public record CreatedUserVacationEvent(Double generalTotalCount,
                                       Double generalUsedCount,
                                       Double alternativeTotalCount,
                                       Double alternativeUsedCount) {
}
