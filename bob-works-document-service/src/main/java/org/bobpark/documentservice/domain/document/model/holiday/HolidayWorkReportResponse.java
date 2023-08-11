package org.bobpark.documentservice.domain.document.model.holiday;

import static org.bobpark.documentservice.domain.user.utils.UserUtils.findByUser;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

import org.bobpark.documentservice.domain.document.entity.holiday.HolidayWorkReport;
import org.bobpark.documentservice.domain.document.model.approval.DocumentApprovalResponse;
import org.bobpark.documentservice.domain.document.type.DocumentStatus;
import org.bobpark.documentservice.domain.document.type.DocumentTypeName;
import org.bobpark.documentservice.domain.user.model.UserResponse;

@Builder
public record HolidayWorkReportResponse(Long id,
                                        DocumentTypeName type,
                                        Long typeId,
                                        UserResponse writer,
                                        DocumentStatus status,
                                        LocalDateTime createdDate,
                                        String createdBy,
                                        LocalDateTime lastModifiedDate,
                                        String lastModifiedBy,
                                        List<DocumentApprovalResponse> approvals,
                                        String workPurpose,
                                        List<HolidayWorkUserResponse> users) {

    public static HolidayWorkReportResponse toResponse(HolidayWorkReport entity, List<UserResponse> users) {
        return HolidayWorkReportResponse.builder()
            .id(entity.getId())
            .type(entity.getType())
            .typeId(entity.getDocumentType().getId())
            .writer(findByUser(users, entity.getWriterId()))
            .status(entity.getStatus())
            .createdDate(entity.getCreatedDate())
            .createdBy(entity.getCreatedBy())
            .lastModifiedDate(entity.getLastModifiedDate())
            .lastModifiedBy(entity.getLastModifiedBy())
            .approvals(
                entity.getApprovals().stream()
                    .map(DocumentApprovalResponse::toResponse)
                    .toList())
            .workPurpose(entity.getWorkPurpose())
            .users(entity.getUsers().stream().map(HolidayWorkUserResponse::toResponse).toList())
            .build();
    }
}
