package org.bobpark.client.domain.document.model.response;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record DocumentTypeApprovalLineStatusResponse(Long id,
                                                     Long uniqueUserId,
                                                     String userId,
                                                     String username,
                                                     Long positionId,
                                                     String positionName,
                                                     String status,
                                                     LocalDateTime approvedDateTime,
                                                     String reason) {
}
