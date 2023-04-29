package org.bobpark.documentservice.domain.document.model.approval;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.entity.DocumentApproval;
import org.bobpark.documentservice.domain.document.model.DocumentResponse;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.user.model.UserResponse;

@Builder
public record DocumentApprovalResponse(Long id,
                                       DocumentResponse document,
                                       DocumentStatus status,
                                       LocalDateTime approvedDateTime,
                                       String reason) {

    public static DocumentApprovalResponse toResponse(DocumentApproval approval) {
        return DocumentApprovalResponse.builder()
            .id(approval.getId())
            .status(approval.getStatus())
            .approvedDateTime(approval.getApprovedDateTime())
            .reason(approval.getReason())
            .build();
    }
}
