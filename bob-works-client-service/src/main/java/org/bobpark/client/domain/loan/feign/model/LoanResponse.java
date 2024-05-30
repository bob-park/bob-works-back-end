package org.bobpark.client.domain.loan.feign.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record LoanResponse(Long id,
                           String name,
                           String description,
                           LocalDate startDate,
                           LocalDate endDate,
                           Integer repaymentDate,
                           Double interestRate,
                           String repaymentType,
                           Long totalBalance,
                           Long repaymentCount,
                           Long endingBalance,
                           List<LoanRepaymentHistoryResponse> repaymentHistories,
                           LocalDateTime createdDate,
                           String createdBy,
                           LocalDateTime lastModifiedDate,
                           String lastModifiedBy) {
}
