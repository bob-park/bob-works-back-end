package org.bobpark.documentservice.domain.document.model;

import static org.bobpark.documentservice.domain.user.utils.UserUtils.*;

import java.util.List;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.entity.DocumentTypeApprovalLine;
import org.bobpark.documentservice.domain.team.model.TeamResponse;
import org.bobpark.documentservice.domain.user.model.UserResponse;
import org.bobpark.documentservice.domain.user.utils.UserUtils;

@Builder
public record DocumentTypeApprovalLineResponse(Long id,
                                               UserResponse user,
                                               TeamResponse team,
                                               DocumentTypeApprovalLineResponse next) {
    public static DocumentTypeApprovalLineResponse toResponse(DocumentTypeApprovalLine approveLine,
        List<UserResponse> users) {
        return DocumentTypeApprovalLineResponse.builder()
            .id(approveLine.getId())
            .user(findByUser(users, approveLine.getUserId()))
            .build();
    }

}
