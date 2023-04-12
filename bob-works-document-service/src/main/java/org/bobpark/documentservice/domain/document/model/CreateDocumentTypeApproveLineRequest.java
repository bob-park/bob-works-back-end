package org.bobpark.documentservice.domain.document.model;

import lombok.Builder;

@Builder
public record CreateDocumentTypeApproveLineRequest(
    Long parentId,
    Long userId
) {
}
