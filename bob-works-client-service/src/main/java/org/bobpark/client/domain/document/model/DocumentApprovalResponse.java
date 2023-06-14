package org.bobpark.client.domain.document.model;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder(toBuilder = true)
public record DocumentApprovalResponse(Long id,
                                       Long lineId,
                                       DocumentResponse document,
                                       String status,
                                       LocalDateTime approvedDateTime,
                                       String reason) {
}
