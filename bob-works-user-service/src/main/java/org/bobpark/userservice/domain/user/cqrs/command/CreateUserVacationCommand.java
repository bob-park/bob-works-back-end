package org.bobpark.userservice.domain.user.cqrs.command;

import lombok.Builder;

@Builder
public record CreateUserVacationCommand(Double generalTotalCount,
                                        Double generalUsedCount,
                                        Double alternativeTotalCount,
                                        Double alternativeUsedCount) {



}
