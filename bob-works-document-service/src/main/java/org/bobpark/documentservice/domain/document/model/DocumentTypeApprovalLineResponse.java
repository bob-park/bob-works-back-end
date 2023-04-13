package org.bobpark.documentservice.domain.document.model;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.entity.DocumentTypeApprovalLine;
import org.bobpark.documentservice.domain.user.model.UserResponse;

@Builder
public record DocumentTypeApprovalLineResponse(
    Long id,
    UserResponse user,
    DocumentTypeApprovalLineResponse next
) {
    public static DocumentTypeApprovalLineResponse toResponse(DocumentTypeApprovalLine approveLine) {
        return DocumentTypeApprovalLineResponse.builder()
            .id(approveLine.getId())
            .user(UserResponse.toResponse(approveLine.getUser()))
            .build();
    }

}
