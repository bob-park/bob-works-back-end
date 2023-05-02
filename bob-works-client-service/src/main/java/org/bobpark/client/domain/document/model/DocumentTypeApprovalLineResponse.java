package org.bobpark.client.domain.document.model;

import lombok.Builder;

import org.bobpark.client.domain.user.model.UserResponse;

@Builder
public record DocumentTypeApprovalLineResponse(Long id,
                                               UserResponse user,
                                               DocumentTypeApprovalLineResponse next) {
}
