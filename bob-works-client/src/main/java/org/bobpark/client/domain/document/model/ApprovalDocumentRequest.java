package org.bobpark.client.domain.document.model;

import lombok.Builder;

@Builder
public record ApprovalDocumentRequest(String status,
                                      String reason) {
}
