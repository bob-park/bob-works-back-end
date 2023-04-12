package org.bobpark.documentservice.domain.document.model;

import java.util.List;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.entity.DocumentTypeApproveLine;
import org.bobpark.documentservice.domain.user.model.UserResponse;

@Builder
public record DocumentTypeApproveLineResponse(
    Long id,
    List<DocumentTypeApproveLineResponse> children,
    UserResponse user
) {
    public static DocumentTypeApproveLineResponse toResponse(DocumentTypeApproveLine approveLine) {
        return DocumentTypeApproveLineResponse.builder()
            .id(approveLine.getId())
            .user(UserResponse.toResponse(approveLine.getUser()))
            .build();
    }

}
