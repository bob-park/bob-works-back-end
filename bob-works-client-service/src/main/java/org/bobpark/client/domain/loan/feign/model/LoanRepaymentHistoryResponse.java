package org.bobpark.client.domain.loan.feign.model;

import java.time.LocalDateTime;

public record LoanRepaymentHistoryResponse(Long id,
                                           Long principal,
                                           Long interest,
                                           Integer round,
                                           Boolean isRepaid,
                                           LocalDateTime repaymentDate,
                                           LocalDateTime createdDate,
                                           LocalDateTime lastModifiedDate) {
}
