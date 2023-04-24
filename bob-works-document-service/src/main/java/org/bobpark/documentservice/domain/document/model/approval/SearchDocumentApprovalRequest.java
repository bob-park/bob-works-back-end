package org.bobpark.documentservice.domain.document.model.approval;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.document.type.DocumentTypeName;

@Builder
public record SearchDocumentApprovalRequest(DocumentTypeName type,
                                            Long writerId,
                                            DocumentStatus status) {
}
