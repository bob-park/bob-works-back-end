package org.bobpark.client.domain.loan.feign.model;

import java.time.LocalDate;

public record CreateLoanRequest(String name,
                                String description,
                                LocalDate startDate,
                                LocalDate endDate,
                                Integer repaymentDate,
                                Double interestRate,
                                String repaymentType,
                                Long totalBalance,
                                Long defaultRepaymentBalance) {
}
