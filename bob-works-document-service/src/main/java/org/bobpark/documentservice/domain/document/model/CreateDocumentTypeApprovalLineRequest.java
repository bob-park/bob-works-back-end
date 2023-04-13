package org.bobpark.documentservice.domain.document.model;

import lombok.Builder;

@Builder
public record CreateDocumentTypeApprovalLineRequest(
    Long parentId,
    Long userId
) {
}
