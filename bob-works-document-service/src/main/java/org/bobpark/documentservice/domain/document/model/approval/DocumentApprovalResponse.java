package org.bobpark.documentservice.domain.document.model.approval;

import java.time.LocalDateTime;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.entity.DocumentApproval;
import org.bobpark.documentservice.domain.document.model.DocumentResponse;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;

@Builder
public record DocumentApprovalResponse(Long id,
                                       DocumentResponse document,
                                       Long lineId,
                                       Long signUserId,
                                       DocumentStatus status,
                                       LocalDateTime approvedDateTime,
                                       String reason) {

    public static DocumentApprovalResponse toResponse(DocumentApproval approval) {
        return DocumentApprovalResponse.builder()
            .id(approval.getId())
            .lineId(approval.getApprovalLine().getId())
            .signUserId(approval.getApprovalLine().getUserId())
            .status(approval.getStatus())
            .approvedDateTime(approval.getApprovedDateTime())
            .reason(approval.getReason())
            .build();
    }
}
