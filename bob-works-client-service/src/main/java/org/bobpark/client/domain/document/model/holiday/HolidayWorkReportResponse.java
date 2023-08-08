package org.bobpark.client.domain.document.model.holiday;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

import org.bobpark.client.domain.user.model.UserResponse;

@Builder
public record HolidayWorkReportResponse(Long id,
                                        String type,
                                        Long typeId,
                                        UserResponse writer,
                                        String status,
                                        LocalDateTime createdDate,
                                        String createdBy,
                                        LocalDateTime lastModifiedDate,
                                        String lastModifiedBy,
                                        String workPurpose,
                                        List<HolidayWorkUserResponse> users) {
}
