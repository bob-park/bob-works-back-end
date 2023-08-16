package org.bobpark.userservice.domain.user.cqrs.command;

public record CreateUserVacationCommand(Double generalTotalCount,
                                        Double generalUsedCount,
                                        Double alternativeTotalCount,
                                        Double alternativeUsedCount) {
}
