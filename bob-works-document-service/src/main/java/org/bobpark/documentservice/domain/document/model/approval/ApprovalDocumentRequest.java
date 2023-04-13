package org.bobpark.documentservice.domain.document.model.approval;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.type.DocumentStatus;

@Builder
public record ApprovalDocumentRequest(DocumentStatus status,
                                      String reason) {
}
