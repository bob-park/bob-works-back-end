package org.bobpark.client.domain.document.model;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record DocumentApprovalResponse(Long id,
                                       DocumentResponse document,
                                       String status,
                                       LocalDateTime approvedDateTime,
                                       String reason) {
}
