package org.bobpark.client.domain.document.model.response;

import java.util.List;

import lombok.Builder;

import org.bobpark.client.domain.document.model.VacationDocumentResponse;

@Builder
public record VacationDocumentDetailResponse(VacationDocumentResponse document,
                                             List<DocumentTypeApprovalLineStatusResponse> lines) {
}
