package org.bobpark.documentservice.domain.document.model.approval;

import java.time.LocalDateTime;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.model.DocumentResponse;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;

@Builder
public record DocumentApprovalResponse(Long id,
                                       DocumentResponse document,
                                       DocumentStatus status,
                                       LocalDateTime approvedDateTime,
                                       String reason) {
}
