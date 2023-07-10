package org.bobpark.client.domain.document.model.response;

import java.util.List;

import lombok.Builder;

import org.bobpark.client.domain.document.model.VacationDocumentResponse;
import org.bobpark.client.domain.user.model.vacation.UserAlternativeVacationResponse;

@Builder
public record VacationDocumentDetailResponse(VacationDocumentResponse document,
                                             List<DocumentTypeApprovalLineStatusResponse> lines,
                                             List<UserAlternativeVacationResponse> useAlternativeVacations) {
}
