package org.bobpark.documentservice.domain.document.model.holiday;

import java.util.List;

public record CreateHolidayWorkReportRequest(Long typeId,
                                             String workPurpose,
                                             List<AddHolidayWorkUserRequest> workUsers) {
}
