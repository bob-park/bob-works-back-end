package org.bobpark.userservice.domain.user.model;

public record CreateUserVacationRequest(double generalTotalCount,
                                        double generalTotalUsedCount,
                                        double alternativeTotalCount,
                                        double alternativeUsedCount) {
}
